import '../src/main/webpack/site/main.scss';

export default {
    title: 'Job Search Results',
};

const mockCards = [
    { title: 'Marketing Manager', location: 'Paris, France', type: 'Full-time' },
    { title: 'Supply Chain Analyst', location: 'Amsterdam, Netherlands', type: 'Full-time' },
    { title: 'R&D Scientist', location: 'Utrecht, Netherlands', type: 'Full-time' },
    { title: 'Digital Project Manager', location: 'London, UK', type: 'Contract' },
    { title: 'HR Business Partner', location: 'Barcelona, Spain', type: 'Full-time' },
    { title: 'Financial Controller', location: 'Warsaw, Poland', type: 'Full-time' },
].map(({ title, location, type }) => `
    <article class="cmp-job-search-results__card" role="listitem">
        <h3 class="cmp-job-search-results__card-title">${title}</h3>
        <p class="cmp-job-search-results__card-location">${location}</p>
        <p class="cmp-job-search-results__card-type">${type}</p>
        <a class="cmp-job-search-results__card-link" href="#" target="_blank">Apply</a>
    </article>
`).join('');

export const WithResults = () => `
<div class="cmp-job-search-results"
     data-cmp-is="job-search-results"
     data-cmp-api-endpoint="/api/jobs"
     data-cmp-items-per-page="6"
     data-cmp-no-results-message="No jobs found matching your search.">
    <div class="cmp-job-search-results__list" role="list">
        ${mockCards}
    </div>
    <p class="cmp-job-search-results__loading" hidden>Loading...</p>
    <p class="cmp-job-search-results__empty" hidden>No jobs found matching your search.</p>
    <button class="cmp-job-search-results__load-more" type="button">Load more</button>
</div>
`;

export const Empty = () => `
<div class="cmp-job-search-results"
     data-cmp-is="job-search-results"
     data-cmp-api-endpoint="/api/jobs"
     data-cmp-items-per-page="10"
     data-cmp-no-results-message="No jobs found matching your search.">
    <div class="cmp-job-search-results__list" role="list"></div>
    <p class="cmp-job-search-results__loading" hidden>Loading...</p>
    <p class="cmp-job-search-results__empty">No jobs found matching your search.</p>
    <button class="cmp-job-search-results__load-more" type="button" hidden>Load more</button>
</div>
`;
