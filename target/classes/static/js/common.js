let HTML, BODY, FOOTER;
let lenis, scheduledAnimationFrame, prevDirection, desktopWidth = 1024, mobileWidth = 768;
const sr = ScrollReveal({
    viewOffset: { bottom: 100 },
    distance: '100px',
    duration: 1200,
    easing: 'cubic-bezier(0.4, 0, 0.2, 1)',
    reset: false,
    beforeReveal: (el) => el.classList.add('sr-animate'),
    beforeReset: (el) => el.classList.remove('sr-animate')
});

window.addEventListener('DOMContentLoaded', () => {
    common.initialize();
    common.header();
    // common.footer();
    // common.sub();
});

window.addEventListener('load', () => {
    variableHeader();
});

const common = {
    initialize() {
        HTML = document.getElementsByTagName('html')[0];
        BODY = document.getElementsByTagName('body')[0];
        FOOTER = document.getElementsByTagName('footer')[0];

        HTML.classList.add('document-loaded');

        handler.initialize();

        window.addEventListener('resize', () => this.onResize());
        window.addEventListener('scroll', () => this.onScroll());

        gsap.registerPlugin(ScrollToPlugin, ScrollTrigger);

        lenis = new Lenis({
            // smoothWheel: false
        });
        lenis.on('scroll', (e) => {
            ScrollTrigger.update;

            // header.toggle(parseInt(e.direction));

            prevDirection = parseInt(e.direction);
        });

        gsap.ticker.add((time) => {
            lenis.raf(time * 1000);
        });

        gsap.ticker.lagSmoothing(0);

        scrollTop.initialize();
        
        mobileScroll();
    },
    onResize() {
        window.addEventListener('resize', () => {
            hamburger.update();
        });
    },
    onScroll() {
        function readAndUpdatePage() {
            // scrollTop.toggle();

            scheduledAnimationFrame = false;
        }

        if(scheduledAnimationFrame) {
            return;
        }

        scheduledAnimationFrame = true;
        requestAnimationFrame(() => readAndUpdatePage());
    },
    header() {
        header.initialize();
        // languageToggle();
    },
    footer() {},
    sub() {
        category();
    }
}

const handler = {
    initialize() {
        this.device = this.isDesktop() ? 'desktop' : 'mobile';

        this.deviceChanging();
    },
    isDesktop() {
        return window.matchMedia('screen and (min-width: ' + (desktopWidth + 1) + 'px)').matches;
    },
    deviceChanging() {
        window.addEventListener('resize', () => {
            // Reload the page when the window is resized
            // (this.device === 'desktop' && window.innerWidth < desktopWidth) && location.reload();
            // (this.device === 'mobile' && window.innerWidth > desktopWidth) && location.reload();
        });
    }
}

const header = {
    initialize() {
        this.el = document.getElementsByClassName('header')[0];
        this.gnb = this.el.getElementsByClassName('gnb')[0];
        this.nav = this.el.getElementsByClassName('nav')[0];
        this.headerMinHeight = this.el.clientHeight;
        this.headerMaxHeight = this.el.getElementsByClassName('depth1')[0].clientHeight;
        
        hamburger.initialize();

        this.nav.addEventListener('mouseenter', this.mouseEnter);
        this.nav.addEventListener('mouseleave', this.mouseLeave);

    },
    mouseEnter(){
        header.headerTheme = header.el.dataset.headerTheme;
        if(!handler.isDesktop()){
            return;
        }
        if (header.headerTheme === 'white'){
            header.el.dataset.headerTheme = 'light';
            header.headerPreviousTheme = 'white';
        }
        if (header.headerTheme === 'transparent'){
            header.el.dataset.headerTheme = 'light';
        }
        header.el.dataset.headerGnb = 'opened';
        gsap.to(header.gnb, { duration: 0.3, height: header.headerMaxHeight });
    },
    mouseLeave() {
        if(!handler.isDesktop()){
            return;
        }
        if (header.headerPreviousTheme === 'white') {
            header.el.dataset.headerTheme = 'white';
            header.headerPreviousTheme = '';
        }
        if (header.headerTheme === 'transparent') {
            header.el.dataset.headerTheme = 'transparent';
        }
        header.el.removeAttribute('data-header-GNB');
        gsap.to(header.gnb, { duration: 0.3, height: header.headerMinHeight });
    },
    toggle(direction) {
        if(prevDirection === direction) {
            return;
        }

        gsap.to('.header', 0.65, { ease: 'power2.out', top: direction > 0 ? -100 : 0 })
    },
}

let hamburger = {
    depth1: '',
    openButton: '',
    closeButton: '',
    currentDevice: '',
    initialize() {
        this.depth1 = header.gnb.querySelectorAll('.header-primary .gnb .depth1 .title');
        this.openButton = header.el.querySelector('.header .hamburger a');
        this.closeButton = header.el.querySelector('.header-primary .gnb .close a');
 
        if(!handler.isDesktop()){
            this.makeBurger();
        }
 
        this.currentDevice = handler.device;
        this.update();
    },
    makeBurger(){
        this.openButton.addEventListener('click', this.openMenu);
        this.closeButton.addEventListener('click', this.closeMenu);
        this.depth1.forEach((element) => element.addEventListener('click', this.accordion) );

        header.gnb.dataset.lenisPrevent = '';

        window.addEventListener('click', (e) => {
            if(e.target.closest('.header')) {
                return;
            }

            console.log('aa');
            
            
            hamburger.closeMenu();
        });
    },
    removeBurger(){
        this.openButton.removeEventListener('click', this.openMenu);
        this.closeButton.removeEventListener('click', this.closeMenu);
        this.depth1.forEach((element) => element.removeEventListener('click', this.accordion) );
    },
    openMenu(){
        lenis.stop();
        header.el.dataset.hamburger = 'opened';
        document.documentElement.style.overflow = 'hidden';

        hamburger.depth1.forEach(element => {
            element.removeAttribute('aria-expanded');
            if(element.nextElementSibling !== null){
                element.nextElementSibling.removeAttribute('style')
            }
        });
    },
    closeMenu(){
        lenis.start();
        header.el.dataset.hamburger = 'closed';
        document.documentElement.style.removeProperty('overflow');
    },
    resetMenu(){
        this.closeMenu();
 
        this.depth1.forEach((element) => {
            element.removeAttribute('aria-expanded');
            if(element.nextElementSibling !== null){
                element.nextElementSibling.removeAttribute('style')
            }
        });
    },
    isChangeDevice(){
        if(handler.device !== this.currentDevice){
            this.currentDevice = handler.getDevice();
            return true;
        }
    },
    update(){
        if(this.isChangeDevice()){
            this.resetMenu();
 
            if(!handler.isDesktop()){
                this.makeBurger();
                handler.resetHeader();
            }else{
                this.removeBurger();
                handler.updateHeader();
            }
        }
    },
    accordion(el) {
        const depth2 = el.target.nextElementSibling;
 
        if (depth2 === null) {
            return;
        }
 
        if (el.target.getAttribute('aria-expanded') === 'true') {
            el.target.setAttribute('aria-expanded', 'false');
            gsap.to(depth2, { duration: 0.3, maxHeight: 0, onComplete: () => { depth2.removeAttribute('style') } });
        } else {
            el.target.setAttribute('aria-expanded', 'true');
            gsap.to(depth2, { duration: 0.3, maxHeight: depth2.scrollHeight, opacity: 1, visibility: 'visible' });
        }
 
        el.preventDefault();
    }
 }

const scrollTop = {
    initialize() {
        const container = document.createElement('div');
        const div = document.createElement('div');
        const button = document.createElement('button');
        const parentClassName = 'footer-floaty';
        const text = HTML.getAttribute('lang') === 'ko' ? '상단으로' : 'Top of page';

        this.el = container;

        container.classList.add(parentClassName);
        div.classList.add(parentClassName + '__top');
        button.classList.add(parentClassName + '__top-button')
        container.appendChild(div);
        div.appendChild(button);
        button.setAttribute('aria-label', text);
        FOOTER.appendChild(container);

        button.addEventListener('click', this.moveTop);
    },
    toggle() {
        // if(window.scrollY > window.innerHeight / 2) {
        //     HTML.dataset.scrollTop = 'true';
        // } else {
        //     HTML.removeAttribute('data-scroll-top');
        // }
    },
    moveTop() {
        gsap.to(window, { duration: 1, ease: 'power3.inOut', scrollTo: 0 });
    },
    pin() {
        // if(window.scrollY > HTML.offsetHeight - window.innerHeight - FOOTER.offsetHeight) {
        //     this.el.dataset.floating = 'false';
        // } else {
        //     this.el.removeAttribute('data-floating');
        // }
    }
}

function languageToggle() {
    const toggle = document.querySelector('.header-language__current');

    if(toggle === null) {
        return;
    }

    toggle.addEventListener('click', (el) => {
        if(toggle.getAttribute('aria-expanded') === 'true') {
            toggle.setAttribute('aria-expanded', 'false');
        } else {
            toggle.setAttribute('aria-expanded', 'true');
        }
    });

    window.addEventListener('click', (e) => {
        if(e.target.parentNode === toggle) {
            return;
        }
        if(e.target !== toggle) {
            toggle.setAttribute('aria-expanded', 'false');
        }
    });
 }

 function variableHeader(){
    const sections = document.querySelectorAll('[data-section-header]');

    if(sections === undefined){
        return;
    }

    sections.forEach((section, i) => {
        let theme = section.dataset.sectionHeader;

        if(theme === ''){
            theme = 'light';
        }

        ScrollTrigger.create({
            trigger: section,
            start: 'top top',
            end: 'bottom top',
            onEnter: () => changeTheme(theme),
            onEnterBack: () => changeTheme(theme),
            onLeave: () => changeTheme(header.theme),
            onLeaveBack: () => changeTheme(header.theme)
        });
    });

    function changeTheme(name){
        header.el.dataset.headerTheme = name;
    }
}

function category() {
    const tabs = document.querySelectorAll('.category-item');

    if(!tabs) {
        return;
    }

    const swiper = new Swiper('.category-list', {
        speed: 300,
        slidesPerView: 'auto',
        slidesPerGroup: 1
    });
    
    tabs.forEach((tab, i) => {
        if(tab.classList.contains('category-item--active')){
            if(i < 3){
                return;
            }
            swiper.slideTo(i, 0);
        }
    });
}

function mobileScroll() {
    const mobileScroll = new Swiper('.mobile-scroll', {
        freeMode: true,
        slidesPerView: 'auto',
        scrollbar: {
            el: '.mobile-scroll .swiper-scrollbar',
            draggable: true,
        },
    });    
}

function openModal(id){
    lenis.stop();
    document.querySelector('#' + id).style.display = "block";
    event.preventDefault();
}

function closeModal(id){
    lenis.start();
    document.querySelector('#' + id).style.display = "none";
}