class FooterComponent extends HTMLElement {
    connectedCallback() {
        this.innerHTML = `
        <footer class="footer">
            <div class="footer-primary">
                <div class="footer-primary__row">
                    <div class="footer-primary__col">
                        <img class="footer-primary__logo" src="/kbinnovationhub_devadm/images/logo.svg" alt="KB Innovation HUB / KB 금융그룹">
                        <ul class="footer-primary__info">
                            <li class="footer-primary__info-item">Address. 서울 영등포구 의사당대로 141, KB국민은행 신관</li>
                            <li class="footer-primary__info-item">E-mail. innohub.khg@kbfg.com</li>
                        </ul>
                        <p class="footer-primary__copyright">© Copyright 2024. KB Innovation HUB All Rights Reserved.</p>
                    </div>
                </div>
            </div>
        </footer>
        `;
    }
}
customElements.define('app-footer', FooterComponent);