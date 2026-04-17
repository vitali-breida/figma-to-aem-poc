import '../src/main/webpack/site/main.scss';

export default {
    title: 'Promo Card',
};

const sampleImage = 'https://images.unsplash.com/photo-1498050108023-c5249f4df085?w=600&auto=format';

export const Default = () => `
<div style="max-width: 700px; padding: 24px;">
    <div class="cmp-promo-card">
        <div class="cmp-promo-card__image">
            <div class="cmp-image">
                <img class="cmp-image__image" src="${sampleImage}" alt="Promo image" />
            </div>
        </div>
        <div class="cmp-promo-card__content">
            <h2 class="cmp-promo-card__title">Build your own team library</h2>
            <p class="cmp-promo-card__description">
                Don't reinvent the wheel with every design. Team libraries let you
                share styles and components across files, with everyone on your team.
            </p>
        </div>
    </div>
</div>
`;
