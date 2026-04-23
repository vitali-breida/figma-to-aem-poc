import '../src/main/webpack/site/main.scss';

export default {
    title: 'Job Search',
};

export const Default = () => `
<div class="cmp-job-search" data-cmp-is="job-search" data-cmp-api-endpoint="/api/jobs">
    <form class="cmp-job-search__form" method="GET">
        <div class="cmp-job-search__fields">
            <input class="cmp-job-search__input cmp-job-search__input--title"
                   type="text"
                   name="q"
                   placeholder="Job title, keywords..."/>
            <input class="cmp-job-search__input cmp-job-search__input--location"
                   type="text"
                   name="location"
                   placeholder="Location"/>
        </div>
        <button class="cmp-job-search__button" type="submit">Search</button>
    </form>
</div>
`;

export const PreFilled = () => `
<div class="cmp-job-search" data-cmp-is="job-search" data-cmp-api-endpoint="/api/jobs">
    <form class="cmp-job-search__form" method="GET">
        <div class="cmp-job-search__fields">
            <input class="cmp-job-search__input cmp-job-search__input--title"
                   type="text"
                   name="q"
                   placeholder="Job title, keywords..."
                   value="Marketing Manager"/>
            <input class="cmp-job-search__input cmp-job-search__input--location"
                   type="text"
                   name="location"
                   placeholder="Location"
                   value="Paris"/>
        </div>
        <button class="cmp-job-search__button" type="submit">Find Jobs</button>
    </form>
</div>
`;
