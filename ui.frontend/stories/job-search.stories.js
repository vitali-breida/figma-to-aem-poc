import '../src/main/webpack/site/main.scss';

export default {
    title: 'Job Search',
};

export const Default = () => `
<div class="cmp-job-search">
    <div class="cmp-job-search__hero">
        <h1 class="cmp-job-search__title">FIND YOUR DANONE JOURNEY TODAY</h1>
    </div>
    <form class="cmp-job-search__form" role="search">
        <div class="cmp-job-search__search-bar">
            <span class="cmp-job-search__search-icon"></span>
            <input class="cmp-job-search__input"
                   type="search"
                   placeholder="Try Procurement, Operation, Quality..." />
        </div>
        <div class="cmp-job-search__filters">
            <div class="cmp-job-search__dropdown">
                <select class="cmp-job-search__select">
                    <option value="">Countries</option>
                    <option>United Kingdom</option>
                    <option>France</option>
                    <option>Germany</option>
                </select>
                <span class="cmp-job-search__chevron"></span>
            </div>
            <div class="cmp-job-search__dropdown">
                <select class="cmp-job-search__select">
                    <option value="">Job Function</option>
                    <option>Marketing</option>
                    <option>Finance</option>
                    <option>Supply Chain</option>
                </select>
                <span class="cmp-job-search__chevron"></span>
            </div>
            <div class="cmp-job-search__dropdown">
                <select class="cmp-job-search__select">
                    <option value="">Experience Level</option>
                    <option>Graduate</option>
                    <option>Mid-level</option>
                    <option>Senior</option>
                </select>
                <span class="cmp-job-search__chevron"></span>
            </div>
        </div>
        <button class="cmp-job-search__submit" type="submit">FIND JOBS</button>
    </form>
</div>
`;
