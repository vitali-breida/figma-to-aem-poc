(() => {
    const SELECTOR = '[data-cmp-is="job-search"]';

    function init(el: HTMLElement): void {
        const apiEndpoint = el.dataset.cmpApiEndpoint || '';
        const form = el.querySelector<HTMLFormElement>('.cmp-job-search__form');
        if (!form) return;

        const qInput = form.querySelector<HTMLInputElement>('[name="q"]');
        const locationSelect = form.querySelector<HTMLSelectElement>('[name="location"]');
        const jobFunctionSelect = form.querySelector<HTMLSelectElement>('[name="jobFunction"]');
        const levelSelect = form.querySelector<HTMLSelectElement>('[name="level"]');

        const params = new URLSearchParams(window.location.search);
        if (qInput && params.get('q')) qInput.value = params.get('q')!;
        if (locationSelect && params.get('location')) locationSelect.value = params.get('location')!;
        if (jobFunctionSelect && params.get('jobFunction')) jobFunctionSelect.value = params.get('jobFunction')!;
        if (levelSelect && params.get('level')) levelSelect.value = params.get('level')!;

        if (apiEndpoint) {
            form.addEventListener('submit', (e: Event) => {
                e.preventDefault();
                const url = new URL(window.location.href);
                url.searchParams.set('q', qInput ? qInput.value.trim() : '');
                url.searchParams.set('location', locationSelect ? locationSelect.value : '');
                url.searchParams.set('jobFunction', jobFunctionSelect ? jobFunctionSelect.value : '');
                url.searchParams.set('level', levelSelect ? levelSelect.value : '');
                window.location.href = url.toString();
            });
        }
    }

    document.querySelectorAll<HTMLElement>(SELECTOR).forEach(init);
})();
