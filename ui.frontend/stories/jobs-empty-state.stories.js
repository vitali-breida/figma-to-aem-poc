import '../src/main/webpack/site/main.scss';

export default {
    title: 'Jobs Empty State',
};

export const Default = () => `
<div class="cmp-jobs-empty-state">
    <h2 class="cmp-jobs-empty-state__heading">OH BUMMER! NO JOBS FOUND!</h2>
    <div class="cmp-jobs-empty-state__cta">
        <a class="cmp-jobs-empty-state__link" href="#">
            <span class="cmp-jobs-empty-state__link-text">SIGN UP FOR OUR JOB ALERT</span>
            <span class="cmp-jobs-empty-state__link-icon" aria-hidden="true"></span>
        </a>
    </div>
</div>
`;

export const CustomHeading = () => `
<div class="cmp-jobs-empty-state">
    <h2 class="cmp-jobs-empty-state__heading">NO RESULTS MATCHING YOUR CRITERIA</h2>
    <div class="cmp-jobs-empty-state__cta">
        <a class="cmp-jobs-empty-state__link" href="#">
            <span class="cmp-jobs-empty-state__link-text">EXPLORE ALL JOBS</span>
            <span class="cmp-jobs-empty-state__link-icon" aria-hidden="true"></span>
        </a>
    </div>
</div>
`;
