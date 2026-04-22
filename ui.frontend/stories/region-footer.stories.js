import '../src/main/webpack/site/main.scss';

export default {
    title: 'Region Footer',
};

export const Default = () => `
<footer class="cmp-region-footer">
    <div class="cmp-region-footer__cta-wrapper">
        <a class="cmp-region-footer__cta" href="#">
            <span class="cmp-region-footer__cta-label">ALL UK AND IRELAND JOBS</span>
            <span class="cmp-region-footer__cta-icon" aria-hidden="true"></span>
        </a>
    </div>
    <nav class="cmp-region-footer__nav" aria-label="Footer primary navigation">
        <ul class="cmp-region-footer__nav-list cmp-region-footer__nav-list--primary">
            <li class="cmp-region-footer__nav-item">
                <a class="cmp-region-footer__nav-link" href="#">Teams</a>
            </li>
            <li class="cmp-region-footer__nav-item">
                <a class="cmp-region-footer__nav-link" href="#">Brands</a>
            </li>
            <li class="cmp-region-footer__nav-item">
                <a class="cmp-region-footer__nav-link" href="#">Our Promise</a>
            </li>
            <li class="cmp-region-footer__nav-item">
                <a class="cmp-region-footer__nav-link" href="#">Life at Danone</a>
            </li>
            <li class="cmp-region-footer__nav-item">
                <a class="cmp-region-footer__nav-link" href="#">Stories</a>
            </li>
        </ul>
        <ul class="cmp-region-footer__nav-list cmp-region-footer__nav-list--secondary">
            <li class="cmp-region-footer__nav-item">
                <a class="cmp-region-footer__nav-link" href="#">Countries</a>
            </li>
            <li class="cmp-region-footer__nav-item">
                <a class="cmp-region-footer__nav-link" href="#">Offices</a>
            </li>
            <li class="cmp-region-footer__nav-item">
                <a class="cmp-region-footer__nav-link" href="#">Factories</a>
            </li>
            <li class="cmp-region-footer__nav-item">
                <a class="cmp-region-footer__nav-link" href="#">Research &amp; Innovation Centres</a>
            </li>
            <li class="cmp-region-footer__nav-item">
                <a class="cmp-region-footer__nav-link" href="#">Graduates &amp; Trainees</a>
            </li>
        </ul>
    </nav>
    <div class="cmp-region-footer__social">
        <a class="cmp-region-footer__social-link cmp-region-footer__social-link--facebook"
           href="#" aria-label="Facebook">
            <span class="cmp-region-footer__social-icon" aria-hidden="true"></span>
        </a>
        <a class="cmp-region-footer__social-link cmp-region-footer__social-link--instagram"
           href="#" aria-label="Instagram">
            <span class="cmp-region-footer__social-icon" aria-hidden="true"></span>
        </a>
        <a class="cmp-region-footer__social-link cmp-region-footer__social-link--linkedin"
           href="#" aria-label="LinkedIn">
            <span class="cmp-region-footer__social-icon" aria-hidden="true"></span>
        </a>
    </div>
    <p class="cmp-region-footer__copyright">COOKIES | PRIVACY POLICY | DANONE.COM</p>
</footer>
`;
