interface Job {
    title: string;
    location: string;
    type?: string;
    applyUrl?: string;
}

(() => {
    const SELECTOR = '[data-cmp-is="job-search-results"]';

    function renderCard(job: Job): HTMLElement {
        const card = document.createElement('article');
        card.className = 'cmp-job-search-results__card';
        card.setAttribute('role', 'listitem');
        card.innerHTML = `
            <h3 class="cmp-job-search-results__card-title">${escapeHtml(job.title)}</h3>
            ${job.location ? `<p class="cmp-job-search-results__card-location">${escapeHtml(job.location)}</p>` : ''}
            ${job.type ? `<p class="cmp-job-search-results__card-type">${escapeHtml(job.type)}</p>` : ''}
            ${job.applyUrl ? `<a class="cmp-job-search-results__card-link" href="${escapeHtml(job.applyUrl)}" target="_blank" rel="noopener">Apply</a>` : ''}
        `;
        return card;
    }

    function escapeHtml(str: string): string {
        return str.replace(/[&<>"']/g, (c) => ({ '&': '&amp;', '<': '&lt;', '>': '&gt;', '"': '&quot;', "'": '&#39;' }[c] || c));
    }

    async function init(el: HTMLElement): Promise<void> {
        const apiEndpoint = el.dataset.cmpApiEndpoint;
        const itemsPerPage = parseInt(el.dataset.cmpItemsPerPage || '10', 10);
        const noResultsMessage = el.dataset.cmpNoResultsMessage || 'No jobs found.';

        const list = el.querySelector<HTMLElement>('.cmp-job-search-results__list');
        const loading = el.querySelector<HTMLElement>('.cmp-job-search-results__loading');
        const empty = el.querySelector<HTMLElement>('.cmp-job-search-results__empty');
        const alertCta = el.querySelector<HTMLElement>('.cmp-job-search-results__alert-cta');
        const loadMore = el.querySelector<HTMLButtonElement>('.cmp-job-search-results__load-more');

        if (!apiEndpoint || !list) return;

        const params = new URLSearchParams(window.location.search);
        const q = params.get('q') || '';
        const location = params.get('location') || '';
        const jobFunction = params.get('jobFunction') || '';
        const level = params.get('level') || '';

        if (!q && !location && !jobFunction && !level) return;

        let allJobs: Job[] = [];
        let offset = 0;

        async function fetchJobs(): Promise<void> {
            if (loading) loading.hidden = false;
            if (loadMore) loadMore.hidden = true;

            try {
                const url = new URL(apiEndpoint!, window.location.origin);
                url.searchParams.set('q', q);
                url.searchParams.set('location', location);
                url.searchParams.set('jobFunction', jobFunction);
                url.searchParams.set('level', level);
                url.searchParams.set('offset', String(offset));
                url.searchParams.set('limit', String(itemsPerPage));

                const res = await fetch(url.toString());
                if (!res.ok) throw new Error(`HTTP ${res.status}`);

                const jobs: Job[] = await res.json();
                allJobs = allJobs.concat(jobs);
                jobs.forEach((job) => list.appendChild(renderCard(job)));

                if (loading) loading.hidden = true;
                if (allJobs.length === 0) {
                    if (empty) {
                        empty.textContent = noResultsMessage;
                        empty.hidden = false;
                    }
                    if (alertCta) alertCta.hidden = false;
                }
                if (jobs.length === itemsPerPage && loadMore) {
                    loadMore.hidden = false;
                }
                offset += jobs.length;
            } catch {
                if (loading) loading.hidden = true;
                if (empty) {
                    empty.textContent = noResultsMessage;
                    empty.hidden = false;
                }
                if (alertCta) alertCta.hidden = false;
            }
        }

        if (loadMore) {
            loadMore.addEventListener('click', fetchJobs);
        }

        await fetchJobs();
    }

    document.querySelectorAll<HTMLElement>(SELECTOR).forEach(init);
})();
