/**
 * Amazon Firestick (Fire OS) - Smart TV Spatial Navigation & Controller Engine
 * Full D-Pad Remote Support, Spatial Navigation, Hero Spotlight Sync, HTML5 Video Player
 */

(function () {
  'use strict';

  // --- STATE MANAGEMENT ---
  const state = {
    activeCategory: 'home',
    currentHeroItem: null,
    focusedElement: null,
    isVideoPlaying: false,
    hudTimer: null,
    currentVideoItem: null,
    sidebarExpanded: false
  };

  // --- DOM REFERENCES ---
  const dom = {
    appContainer: document.getElementById('app-container'),
    sidebarNav: document.getElementById('sidebar-nav'),
    navMenuList: document.getElementById('nav-menu-list'),
    mainStage: document.getElementById('main-stage'),
    heroSpotlight: document.getElementById('hero-spotlight'),
    heroBackdropImg: document.getElementById('hero-backdrop-img'),
    heroBadgesRow: document.getElementById('hero-badges-row'),
    heroTitle: document.getElementById('hero-title'),
    heroMetadata: document.getElementById('hero-metadata'),
    heroDescription: document.getElementById('hero-description'),
    heroBtnPlay: document.getElementById('hero-btn-play'),
    heroBtnInfo: document.getElementById('hero-btn-info'),
    contentFeed: document.getElementById('content-feed'),
    // Video Player
    videoModal: document.getElementById('video-player-modal'),
    videoElement: document.getElementById('html5-video-player'),
    playerHud: document.getElementById('player-hud'),
    playerBtnBack: document.getElementById('player-btn-back'),
    playerVideoTitle: document.getElementById('player-video-title'),
    playerVideoSubtitle: document.getElementById('player-video-subtitle'),
    playerCurrentTime: document.getElementById('player-current-time'),
    playerDurationTime: document.getElementById('player-duration-time'),
    playerScrubberFill: document.getElementById('player-scrubber-fill'),
    playerScrubberThumb: document.getElementById('player-scrubber-thumb'),
    playerBtnRewind: document.getElementById('player-btn-rewind'),
    playerBtnTogglePlay: document.getElementById('player-btn-toggle-play'),
    playerBtnForward: document.getElementById('player-btn-forward'),
    playerIconPlay: document.getElementById('player-icon-play'),
    playerIconPause: document.getElementById('player-icon-pause'),
    // Modals & Overlays
    infoModal: document.getElementById('info-modal'),
    modalInfoTitle: document.getElementById('modal-info-title'),
    modalInfoTagline: document.getElementById('modal-info-tagline'),
    modalInfoDesc: document.getElementById('modal-info-desc'),
    modalBtnClose: document.getElementById('modal-btn-close'),
    modalBtnPlay: document.getElementById('modal-btn-play'),
    modalBtnWatchlist: document.getElementById('modal-btn-watchlist'),
    searchOverlay: document.getElementById('search-overlay'),
    searchBtnBack: document.getElementById('search-btn-back'),
    searchInput: document.getElementById('search-input'),
    searchResultsGrid: document.getElementById('search-results-grid'),
    fireToast: document.getElementById('fire-toast'),
    toastMessage: document.getElementById('toast-message')
  };

  // SVG Icons for Navigation
  const NAV_ICONS = {
    search: '<svg viewBox="0 0 24 24"><path d="M15.5 14h-.79l-.28-.27A6.471 6.471 0 0 0 16 9.5 6.5 6.5 0 1 0 9.5 16c1.61 0 3.09-.59 4.23-1.57l.27.28v.79l5 4.99L20.49 19l-4.99-5zm-6 0C7.01 14 5 11.99 5 9.5S7.01 5 9.5 5 14 7.01 14 9.5 11.99 14 9.5 14z"/></svg>',
    home: '<svg viewBox="0 0 24 24"><path d="M10 20v-6h4v6h5v-8h3L12 3 2 12h3v8z"/></svg>',
    live: '<svg viewBox="0 0 24 24"><path d="M21 3H3c-1.1 0-2 .9-2 2v12c0 1.1.9 2 2 2h5v2h8v-2h5c1.1 0 1.99-.9 1.99-2L23 5c0-1.1-.9-2-2-2zm0 14H3V5h18v12zm-8.5-6.5L10 9v6l4.5-2.5z"/></svg>',
    movie: '<svg viewBox="0 0 24 24"><path d="M18 4l2 4h-3l-2-4h-2l2 4h-3l-2-4H8l2 4H7L5 4H4c-1.1 0-1.99.9-1.99 2L2 18c0 1.1.9 2 2 2h16c1.1 0 2-.9 2-2V4h-4z"/></svg>',
    tv: '<svg viewBox="0 0 24 24"><path d="M21 3H3c-1.1 0-2 .9-2 2v12c0 1.1.9 2 2 2h5v2h8v-2h5c1.1 0 1.99-.9 1.99-2L23 5c0-1.1-.9-2-2-2zm0 14H3V5h18v12z"/></svg>',
    apps: '<svg viewBox="0 0 24 24"><path d="M4 8h4V4H4v4zm6 12h4v-4h-4v4zm-6 0h4v-4H4v4zm0-6h4v-4H4v4zm6 0h4v-4h-4v4zm6-10v4h4V4h-4zm-6 4h4V4h-4v4zm6 6h4v-4h-4v4zm0 6h4v-4h-4v4z"/></svg>',
    settings: '<svg viewBox="0 0 24 24"><path d="M19.14 12.94c.04-.3.06-.61.06-.94 0-.32-.02-.64-.07-.94l2.03-1.58c.18-.14.23-.41.12-.61l-1.92-3.32c-.12-.22-.37-.29-.59-.22l-2.39.96c-.5-.38-1.03-.7-1.62-.94l-.36-2.54c-.04-.24-.24-.41-.48-.41h-3.84c-.24 0-.43.17-.47.41l-.36 2.54c-.59.24-1.13.57-1.62.94l-2.39-.96c-.22-.08-.47 0-.59.22L2.74 8.87c-.12.21-.08.47.12.61l2.03 1.58c-.05.3-.09.63-.09.94s.02.64.07.94l-2.03 1.58c-.18.14-.23.41-.12.61l1.92 3.32c.12.22.37.29.59.22l2.39-.96c.5.38 1.03.7 1.62.94l.36 2.54c.05.24.24.41.48.41h3.84c.24 0 .44-.17.47-.41l.36-2.54c.59-.24 1.13-.56 1.62-.94l2.39.96c.22.08.47 0 .59-.22l1.92-3.32c.12-.22.07-.47-.12-.61l-2.01-1.58zM12 15.6c-1.98 0-3.6-1.62-3.6-3.6s1.62-3.6 3.6-3.6 3.6 1.62 3.6 3.6-1.62 3.6-3.6 3.6z"/></svg>'
  };

  // --- INITIALIZATION ---
  function init() {
    renderSidebar();
    renderContentRows();
    updateHeroSpotlight(FIRE_TV_DATA.heroSpotlightDefault);
    setupEventListeners();

    // Default Focus: first card of row 0
    setTimeout(() => {
      const firstCard = document.querySelector('.media-card');
      if (firstCard) {
        setFocus(firstCard);
      } else {
        setFocus(dom.heroBtnPlay);
      }
    }, 150);
  }

  // --- RENDER SIDEBAR ---
  function renderSidebar() {
    dom.navMenuList.innerHTML = '';
    FIRE_TV_DATA.sidebarItems.forEach(item => {
      const li = document.createElement('li');
      const btn = document.createElement('button');
      btn.className = `nav-item-btn ${item.active ? 'active' : ''}`;
      btn.id = item.id;
      btn.tabIndex = 0;
      btn.dataset.category = item.category;
      btn.innerHTML = `
        <div class="nav-icon">${NAV_ICONS[item.icon] || ''}</div>
        <span class="nav-label">${item.label}</span>
      `;

      btn.addEventListener('click', () => handleNavSelection(item.category, btn));
      li.appendChild(btn);
      dom.navMenuList.appendChild(li);
    });
  }

  // --- RENDER CONTENT ROWS ---
  function renderContentRows() {
    dom.contentFeed.innerHTML = '';
    FIRE_TV_DATA.rows.forEach((row, rowIndex) => {
      const rowSection = document.createElement('section');
      rowSection.className = 'content-row';
      rowSection.dataset.rowIndex = rowIndex;
      rowSection.id = row.id;

      const header = document.createElement('div');
      header.className = 'row-header';
      header.innerHTML = `
        <h2 class="row-title">${row.title}</h2>
        <span class="row-counter">${row.items.length} Títulos</span>
      `;

      const viewport = document.createElement('div');
      viewport.className = 'carousel-viewport';

      const track = document.createElement('div');
      track.className = 'carousel-track';

      row.items.forEach((item, colIndex) => {
        const card = createMediaCard(item, row.type, rowIndex, colIndex);
        track.appendChild(card);
      });

      viewport.appendChild(track);
      rowSection.appendChild(header);
      rowSection.appendChild(viewport);
      dom.contentFeed.appendChild(rowSection);
    });
  }

  // --- CREATE MEDIA CARD ---
  function createMediaCard(item, rowType, rowIndex, colIndex) {
    const card = document.createElement('div');
    const isPoster = item.aspectRatio === '2:3';
    card.className = `media-card ${isPoster ? 'aspect-2-3' : 'aspect-16-9'}`;
    card.tabIndex = 0;
    card.dataset.rowIndex = rowIndex;
    card.dataset.colIndex = colIndex;
    card.dataset.id = item.id;

    // Attach full data object for instant retrieval
    card._itemData = item;

    let innerContent = '';

    if (rowType === 'apps') {
      // App Card
      innerContent = `
        <div class="card-img-container" style="background: ${item.bgColor || '#141b26'};">
          <div class="app-card-content">
            <div class="app-card-icon" style="background: ${item.iconColor}; color: #fff;">
              <svg viewBox="0 0 24 24" fill="#ffffff"><path d="M4 8h4V4H4v4zm6 12h4v-4h-4v4zm-6 0h4v-4H4v4zm0-6h4v-4H4v4zm6 0h4v-4h-4v4zm6-10v4h4V4h-4zm-6 4h4V4h-4v4zm6 6h4v-4h-4v4zm0 6h4v-4h-4v4z"/></svg>
            </div>
            <span class="card-title">${item.title}</span>
            <span class="card-subtitle">${item.category}</span>
          </div>
        </div>
      `;
    } else {
      // Video / TV Show / Live Channel Card
      const progressMarkup = item.progress ? `
        <div class="progress-bar-container">
          <div class="progress-bar-fill" style="width: ${item.progress}%"></div>
        </div>
      ` : '';

      const liveBadgeMarkup = item.badge === 'EN VIVO' ? `
        <div class="live-badge-tag">
          <span class="live-pulse"></span>
          <span>EN VIVO</span>
        </div>
      ` : '';

      const subtitleText = item.episode || item.showTitle || item.tagline || (item.year ? `${item.year} • ${item.rating || 'HD'}` : '');

      innerContent = `
        <div class="card-img-container">
          <img class="card-img" src="${item.image}" alt="${item.title}" loading="lazy">
          <div class="card-vignette"></div>
          ${liveBadgeMarkup}
          <div class="card-info-overlay">
            <span class="card-title">${item.title}</span>
            <span class="card-subtitle">${subtitleText}</span>
          </div>
          ${progressMarkup}
        </div>
      `;
    }

    card.innerHTML = innerContent;

    // Mouse / Touch click handler
    card.addEventListener('click', () => {
      executeAction(card);
    });

    return card;
  }

  // --- UPDATE HERO SPOTLIGHT ---
  function updateHeroSpotlight(item) {
    if (!item) return;
    state.currentHeroItem = item;

    // Update backdrop with smooth crossfade
    const img = dom.heroBackdropImg;
    img.classList.remove('visible');
    const newImgSrc = item.backdrop || item.image;

    // Create preload image for seamless transition
    const tempImg = new Image();
    tempImg.onload = () => {
      img.src = newImgSrc;
      img.classList.add('visible');
    };
    tempImg.src = newImgSrc;

    // Update Badges
    dom.heroBadgesRow.innerHTML = '';
    if (item.badges && item.badges.length) {
      item.badges.forEach(badge => {
        const span = document.createElement('span');
        if (badge.toLowerCase().includes('prime')) {
          span.className = 'badge-prime';
        } else if (badge.toLowerCase().includes('score') || badge.toLowerCase().includes('imdb')) {
          span.className = 'badge-pill badge-score';
        } else {
          span.className = 'badge-pill';
        }
        span.textContent = badge;
        dom.heroBadgesRow.appendChild(span);
      });
    } else {
      const defaultBadge = document.createElement('span');
      defaultBadge.className = 'badge-prime';
      defaultBadge.textContent = 'PRIME VIDEO';
      dom.heroBadgesRow.appendChild(defaultBadge);
    }

    // Update Title & Text
    dom.heroTitle.textContent = item.title;

    // Metadata
    const metaParts = [];
    if (item.year) metaParts.push(item.year);
    if (item.rating) metaParts.push(item.rating);
    if (item.duration) metaParts.push(item.duration);
    if (item.score) metaParts.push(`★ ${item.score}`);

    dom.heroMetadata.innerHTML = metaParts
      .map(part => `<span>${part}</span>`)
      .join('<span class="dot"></span>');

    dom.heroDescription.textContent = item.description || item.tagline || 'Disfruta de la mejor calidad de transmisión en Fire TV.';
  }

  // --- SPATIAL NAVIGATION CORE ---
  function setFocus(element, scrollIntoView = true) {
    if (!element) return;

    if (state.focusedElement) {
      state.focusedElement.classList.remove('focused');
      state.focusedElement.blur();
    }

    state.focusedElement = element;
    element.classList.add('focused');
    element.focus();

    // Check if element is inside sidebar
    const inSidebar = dom.sidebarNav.contains(element);
    if (inSidebar) {
      dom.sidebarNav.classList.add('expanded');
      state.sidebarExpanded = true;
    } else {
      dom.sidebarNav.classList.remove('expanded');
      state.sidebarExpanded = false;
    }

    // If focused on a media card: update Hero Spotlight dynamically!
    if (element.classList.contains('media-card') && element._itemData) {
      updateHeroSpotlight(element._itemData);

      if (scrollIntoView) {
        // Horizontal scroll within carousel track
        element.scrollIntoView({ behavior: 'smooth', block: 'nearest', inline: 'center' });

        // Vertical scroll of main stage to reveal row
        const row = element.closest('.content-row');
        if (row) {
          const rowRect = row.getBoundingClientRect();
          const stageRect = dom.mainStage.getBoundingClientRect();
          if (rowRect.top < stageRect.top + 100 || rowRect.bottom > stageRect.bottom - 50) {
            row.scrollIntoView({ behavior: 'smooth', block: 'center' });
          }
        }
      }
    }
  }

  // --- D-PAD MOVEMENT HANDLERS ---
  function handleArrowNavigation(direction) {
    const current = state.focusedElement;
    if (!current) {
      const firstCard = document.querySelector('.media-card');
      if (firstCard) setFocus(firstCard);
      return;
    }

    // Modal active? Navigate inside modal
    if (state.isVideoPlaying) {
      handleVideoPlayerNav(direction);
      return;
    }
    if (dom.infoModal.classList.contains('active')) {
      handleInfoModalNav(direction);
      return;
    }
    if (dom.searchOverlay.classList.contains('active')) {
      handleSearchNav(direction);
      return;
    }

    // Check current zone
    const isSidebar = dom.sidebarNav.contains(current);
    const isHeroAction = current.classList.contains('btn-hero');
    const isMediaCard = current.classList.contains('media-card');

    if (isSidebar) {
      handleSidebarNav(direction, current);
    } else if (isHeroAction) {
      handleHeroActionNav(direction, current);
    } else if (isMediaCard) {
      handleMediaCardNav(direction, current);
    }
  }

  function handleSidebarNav(direction, current) {
    const navButtons = Array.from(dom.sidebarNav.querySelectorAll('.nav-item-btn'));
    const currentIndex = navButtons.indexOf(current);

    if (direction === 'down') {
      if (currentIndex < navButtons.length - 1) {
        setFocus(navButtons[currentIndex + 1]);
      }
    } else if (direction === 'up') {
      if (currentIndex > 0) {
        setFocus(navButtons[currentIndex - 1]);
      }
    } else if (direction === 'right') {
      // Exit sidebar to main content
      const firstCard = document.querySelector('.media-card');
      if (firstCard) {
        setFocus(firstCard);
      } else {
        setFocus(dom.heroBtnPlay);
      }
    }
  }

  function handleHeroActionNav(direction, current) {
    if (direction === 'left') {
      if (current === dom.heroBtnInfo) {
        setFocus(dom.heroBtnPlay);
      } else {
        // Move into sidebar
        const activeNav = dom.sidebarNav.querySelector('.nav-item-btn.active') || dom.sidebarNav.querySelector('.nav-item-btn');
        setFocus(activeNav);
      }
    } else if (direction === 'right') {
      if (current === dom.heroBtnPlay) {
        setFocus(dom.heroBtnInfo);
      }
    } else if (direction === 'down') {
      // Jump to first row first item
      const firstCard = document.querySelector('.media-card');
      if (firstCard) {
        setFocus(firstCard);
      }
    }
  }

  function handleMediaCardNav(direction, current) {
    const rowIndex = parseInt(current.dataset.rowIndex, 10);
    const colIndex = parseInt(current.dataset.colIndex, 10);

    const currentRow = document.querySelector(`.content-row[data-row-index="${rowIndex}"]`);
    const cardsInRow = currentRow ? Array.from(currentRow.querySelectorAll('.media-card')) : [];

    if (direction === 'left') {
      if (colIndex > 0 && cardsInRow[colIndex - 1]) {
        setFocus(cardsInRow[colIndex - 1]);
      } else {
        // Navigating left from first item enters the Collapsible Sidebar!
        const activeNav = dom.sidebarNav.querySelector('.nav-item-btn.active') || dom.sidebarNav.querySelector('.nav-item-btn');
        setFocus(activeNav);
      }
    } else if (direction === 'right') {
      if (colIndex < cardsInRow.length - 1 && cardsInRow[colIndex + 1]) {
        setFocus(cardsInRow[colIndex + 1]);
      }
    } else if (direction === 'up') {
      if (rowIndex === 0) {
        // Move up to Hero buttons
        dom.mainStage.scrollTo({ top: 0, behavior: 'smooth' });
        setFocus(dom.heroBtnPlay);
      } else {
        // Move to row above, closest column
        const prevRow = document.querySelector(`.content-row[data-row-index="${rowIndex - 1}"]`);
        if (prevRow) {
          const prevCards = Array.from(prevRow.querySelectorAll('.media-card'));
          const targetCard = prevCards[Math.min(colIndex, prevCards.length - 1)];
          if (targetCard) setFocus(targetCard);
        }
      }
    } else if (direction === 'down') {
      // Move to row below
      const nextRow = document.querySelector(`.content-row[data-row-index="${rowIndex + 1}"]`);
      if (nextRow) {
        const nextCards = Array.from(nextRow.querySelectorAll('.media-card'));
        const targetCard = nextCards[Math.min(colIndex, nextCards.length - 1)];
        if (targetCard) setFocus(targetCard);
      }
    }
  }

  // --- ACTIONS (ENTER / OK) ---
  function executeAction(element) {
    if (!element) return;

    // 1. Media Card Clicked -> Play Video
    if (element.classList.contains('media-card')) {
      const itemData = element._itemData;
      if (itemData) {
        if (itemData.category && itemData.category.includes('Streaming')) {
          showToast(`Abriendo aplicación: ${itemData.title}`);
        } else {
          playVideo(itemData);
        }
      }
      return;
    }

    // 2. Hero Play Button
    if (element === dom.heroBtnPlay) {
      if (state.currentHeroItem) {
        playVideo(state.currentHeroItem);
      }
      return;
    }

    // 3. Hero Info Button
    if (element === dom.heroBtnInfo) {
      if (state.currentHeroItem) {
        openInfoModal(state.currentHeroItem);
      }
      return;
    }

    // 4. Sidebar navigation buttons
    if (element.classList.contains('nav-item-btn')) {
      const category = element.dataset.category;
      handleNavSelection(category, element);
      return;
    }

    // 5. Video Player controls
    if (element === dom.playerBtnBack) {
      closeVideoPlayer();
      return;
    }
    if (element === dom.playerBtnTogglePlay) {
      toggleVideoPlayback();
      return;
    }
    if (element === dom.playerBtnRewind) {
      seekVideo(-10);
      return;
    }
    if (element === dom.playerBtnForward) {
      seekVideo(10);
      return;
    }

    // 6. Info modal buttons
    if (element === dom.modalBtnClose) {
      closeInfoModal();
      return;
    }
    if (element === dom.modalBtnPlay) {
      closeInfoModal();
      if (state.currentHeroItem) playVideo(state.currentHeroItem);
      return;
    }
    if (element === dom.modalBtnWatchlist) {
      showToast('✓ Agregado a tu lista de seguimiento');
      closeInfoModal();
      return;
    }

    // 7. Search back
    if (element === dom.searchBtnBack) {
      closeSearchOverlay();
      return;
    }
  }

  // --- BACK / ESCAPE KEY HANDLER ---
  function handleBackAction() {
    if (state.isVideoPlaying) {
      closeVideoPlayer();
      return;
    }
    if (dom.infoModal.classList.contains('active')) {
      closeInfoModal();
      return;
    }
    if (dom.searchOverlay.classList.contains('active')) {
      closeSearchOverlay();
      return;
    }

    // If currently in main stage: focus the sidebar
    if (!dom.sidebarNav.contains(state.focusedElement)) {
      const activeNav = dom.sidebarNav.querySelector('.nav-item-btn.active') || dom.sidebarNav.querySelector('.nav-item-btn');
      setFocus(activeNav);
    } else {
      showToast('Presiona INICIO para ver recomendaciones');
    }
  }

  // --- VIDEO PLAYER ENGINE ---
  function playVideo(item) {
    if (!item || !item.videoUrl) {
      showToast('Enlace de video no disponible');
      return;
    }

    state.currentVideoItem = item;
    state.isVideoPlaying = true;

    dom.playerVideoTitle.textContent = item.title;
    dom.playerVideoSubtitle.textContent = item.episode || item.showTitle || `${item.duration || 'Full HD'} • Sonido Dolby`;

    dom.videoElement.src = item.videoUrl;
    dom.videoModal.classList.add('active');

    // Attempt video playback
    const playPromise = dom.videoElement.play();
    if (playPromise !== undefined) {
      playPromise
        .then(() => updatePlayPauseIcons(true))
        .catch(err => {
          console.warn('Playback error or user gesture required:', err);
          updatePlayPauseIcons(false);
        });
    }

    showPlayerHud();
    setFocus(dom.playerBtnTogglePlay);
  }

  function toggleVideoPlayback() {
    if (dom.videoElement.paused) {
      dom.videoElement.play();
      updatePlayPauseIcons(true);
      showToast('Reproduciendo');
    } else {
      dom.videoElement.pause();
      updatePlayPauseIcons(false);
      showToast('Pausa');
    }
    showPlayerHud();
  }

  function seekVideo(seconds) {
    if (!dom.videoElement.duration) return;
    dom.videoElement.currentTime = Math.max(0, Math.min(dom.videoElement.duration, dom.videoElement.currentTime + seconds));
    showToast(seconds > 0 ? `+${seconds}s Adelantar` : `${seconds}s Retroceder`);
    showPlayerHud();
  }

  function updatePlayPauseIcons(isPlaying) {
    dom.playerIconPlay.style.display = isPlaying ? 'none' : 'block';
    dom.playerIconPause.style.display = isPlaying ? 'block' : 'none';
  }

  function closeVideoPlayer() {
    dom.videoElement.pause();
    dom.videoElement.removeAttribute('src');
    dom.videoElement.load();
    dom.videoModal.classList.remove('active');
    state.isVideoPlaying = false;

    // Restore focus to hero or first card
    setFocus(dom.heroBtnPlay);
  }

  function showPlayerHud() {
    dom.playerHud.classList.remove('hud-hidden');
    clearTimeout(state.hudTimer);
    state.hudTimer = setTimeout(() => {
      if (state.isVideoPlaying && !dom.videoElement.paused) {
        dom.playerHud.classList.add('hud-hidden');
      }
    }, 4500);
  }

  function handleVideoPlayerNav(direction) {
    showPlayerHud();
    const current = state.focusedElement;
    const playerButtons = [dom.playerBtnBack, dom.playerBtnRewind, dom.playerBtnTogglePlay, dom.playerBtnForward];

    if (direction === 'up') {
      setFocus(dom.playerBtnBack);
    } else if (direction === 'down') {
      setFocus(dom.playerBtnTogglePlay);
    } else if (direction === 'left') {
      if (current === dom.playerBtnForward) setFocus(dom.playerBtnTogglePlay);
      else if (current === dom.playerBtnTogglePlay) setFocus(dom.playerBtnRewind);
      else if (current === dom.playerBtnRewind) seekVideo(-10);
    } else if (direction === 'right') {
      if (current === dom.playerBtnRewind) setFocus(dom.playerBtnTogglePlay);
      else if (current === dom.playerBtnTogglePlay) setFocus(dom.playerBtnForward);
      else if (current === dom.playerBtnForward) seekVideo(10);
    }
  }

  // --- INFO MODAL ---
  function openInfoModal(item) {
    dom.modalInfoTitle.textContent = item.title;
    dom.modalInfoTagline.textContent = item.tagline || (item.badges ? item.badges.join(' • ') : '');
    dom.modalInfoDesc.textContent = item.description || 'Detalles no disponibles.';
    dom.infoModal.classList.add('active');
    setFocus(dom.modalBtnPlay);
  }

  function closeInfoModal() {
    dom.infoModal.classList.remove('active');
    setFocus(dom.heroBtnInfo);
  }

  function handleInfoModalNav(direction) {
    const current = state.focusedElement;
    if (direction === 'left' && current === dom.modalBtnWatchlist) {
      setFocus(dom.modalBtnPlay);
    } else if (direction === 'right' && current === dom.modalBtnPlay) {
      setFocus(dom.modalBtnWatchlist);
    } else if (direction === 'up') {
      setFocus(dom.modalBtnClose);
    } else if (direction === 'down' && current === dom.modalBtnClose) {
      setFocus(dom.modalBtnPlay);
    }
  }

  // --- SEARCH OVERLAY ---
  function openSearchOverlay() {
    dom.searchOverlay.classList.add('active');
    dom.searchInput.value = '';
    renderSearchResults('');
    setFocus(dom.searchInput);
  }

  function closeSearchOverlay() {
    dom.searchOverlay.classList.remove('active');
    const homeBtn = dom.sidebarNav.querySelector('#nav-home');
    setFocus(homeBtn);
  }

  function renderSearchResults(query) {
    dom.searchResultsGrid.innerHTML = '';
    const q = query.toLowerCase().trim();

    // Flatten all items
    const allItems = [];
    FIRE_TV_DATA.rows.forEach(r => allItems.push(...r.items));

    const filtered = q
      ? allItems.filter(i => (i.title && i.title.toLowerCase().includes(q)) || (i.description && i.description.toLowerCase().includes(q)))
      : allItems.slice(0, 8);

    filtered.forEach((item, idx) => {
      const card = createMediaCard(item, 'posters', 99, idx);
      dom.searchResultsGrid.appendChild(card);
    });
  }

  function handleSearchNav(direction) {
    const current = state.focusedElement;
    if (current === dom.searchInput) {
      if (direction === 'down') {
        const firstResult = dom.searchResultsGrid.querySelector('.media-card');
        if (firstResult) setFocus(firstResult);
      } else if (direction === 'left') {
        setFocus(dom.searchBtnBack);
      }
    } else if (current === dom.searchBtnBack) {
      if (direction === 'right') setFocus(dom.searchInput);
    } else if (current.classList.contains('media-card')) {
      if (direction === 'up') setFocus(dom.searchInput);
    }
  }

  // --- NAVIGATION CATEGORY FILTERING ---
  function handleNavSelection(category, btnElement) {
    // Update active UI tab
    dom.sidebarNav.querySelectorAll('.nav-item-btn').forEach(b => b.classList.remove('active'));
    if (btnElement) btnElement.classList.add('active');
    state.activeCategory = category;

    if (category === 'search') {
      openSearchOverlay();
      return;
    }

    if (category === 'live') {
      const liveRow = document.getElementById('row-live-tv');
      if (liveRow) {
        liveRow.scrollIntoView({ behavior: 'smooth', block: 'start' });
        const firstCard = liveRow.querySelector('.media-card');
        if (firstCard) setFocus(firstCard);
      }
      showToast('Canales de TV en Vivo cargados');
      return;
    }

    if (category === 'apps') {
      const appsRow = document.getElementById('row-my-apps');
      if (appsRow) {
        appsRow.scrollIntoView({ behavior: 'smooth', block: 'start' });
        const firstCard = appsRow.querySelector('.media-card');
        if (firstCard) setFocus(firstCard);
      }
      showToast('Mis Aplicaciones y Canales');
      return;
    }

    if (category === 'settings') {
      showToast('Ajustes de Fire TV: Red Wi-Fi, Pantalla y Cuentas');
      return;
    }

    // Default Home
    dom.mainStage.scrollTo({ top: 0, behavior: 'smooth' });
    const firstCard = document.querySelector('.media-card');
    if (firstCard) setFocus(firstCard);
  }

  // --- TOAST NOTIFICATION ---
  function showToast(msg) {
    dom.toastMessage.textContent = msg;
    dom.fireToast.classList.add('active');
    clearTimeout(dom.toastTimer);
    dom.toastTimer = setTimeout(() => {
      dom.fireToast.classList.remove('active');
    }, 3200);
  }

  // --- TIMELINE / PROGRESS TRACKING ---
  function setupVideoTimeline() {
    dom.videoElement.addEventListener('timeupdate', () => {
      const current = dom.videoElement.currentTime;
      const duration = dom.videoElement.duration || 0;

      dom.playerCurrentTime.textContent = formatTime(current);
      dom.playerDurationTime.textContent = formatTime(duration);

      const percent = duration > 0 ? (current / duration) * 100 : 0;
      dom.playerScrubberFill.style.width = `${percent}%`;
      dom.playerScrubberThumb.style.left = `${percent}%`;
    });

    dom.videoElement.addEventListener('ended', () => {
      showToast('Reproducción finalizada');
      closeVideoPlayer();
    });
  }

  function formatTime(seconds) {
    if (isNaN(seconds) || seconds < 0) return '00:00';
    const mins = Math.floor(seconds / 60);
    const secs = Math.floor(seconds % 60);
    return `${mins < 10 ? '0' : ''}${mins}:${secs < 10 ? '0' : ''}${secs}`;
  }

  // --- KEYBOARD & D-PAD EVENT LISTENER ---
  function setupEventListeners() {
    setupVideoTimeline();

    // Search input live query
    dom.searchInput.addEventListener('input', (e) => {
      renderSearchResults(e.target.value);
    });

    // Main Keyboard & TV Remote Listener
    window.addEventListener('keydown', (e) => {
      // Map both modern event.key and legacy keyCode for Android TV / Fire TV compatibility
      const key = e.key;
      const code = e.keyCode;

      // Prevent default page scroll on Arrow keys & Space
      if (['ArrowUp', 'ArrowDown', 'ArrowLeft', 'ArrowRight', 'Space'].includes(key) ||
          [38, 40, 37, 39, 32].includes(code)) {
        if (document.activeElement !== dom.searchInput) {
          e.preventDefault();
        }
      }

      // 1. Directional Navigation
      if (key === 'ArrowUp' || code === 38 || code === 19) {
        handleArrowNavigation('up');
      } else if (key === 'ArrowDown' || code === 40 || code === 20) {
        handleArrowNavigation('down');
      } else if (key === 'ArrowLeft' || code === 37 || code === 21) {
        handleArrowNavigation('left');
      } else if (key === 'ArrowRight' || code === 39 || code === 22) {
        handleArrowNavigation('right');
      }
      // 2. Select / Enter / OK
      else if (key === 'Enter' || code === 13 || code === 23 || code === 66) {
        e.preventDefault();
        executeAction(state.focusedElement || document.activeElement);
      }
      // 3. Back / Escape
      else if (key === 'Escape' || key === 'Backspace' || code === 27 || code === 4 || code === 8) {
        if (document.activeElement === dom.searchInput && key === 'Backspace') {
          // Allow backspace in search input
          return;
        }
        e.preventDefault();
        handleBackAction();
      }
      // 4. Media Play/Pause keys
      else if (code === 85 || code === 126 || code === 127 || code === 179) { // Android media keys
        if (state.isVideoPlaying) {
          toggleVideoPlayback();
        } else if (state.currentHeroItem) {
          playVideo(state.currentHeroItem);
        }
      }
    });

    // Window global method for Android Native Bridge if needed
    window.fireTvRemoteDispatch = function (action) {
      switch (action) {
        case 'UP': handleArrowNavigation('up'); break;
        case 'DOWN': handleArrowNavigation('down'); break;
        case 'LEFT': handleArrowNavigation('left'); break;
        case 'RIGHT': handleArrowNavigation('right'); break;
        case 'SELECT': executeAction(state.focusedElement || document.activeElement); break;
        case 'BACK': handleBackAction(); break;
        case 'PLAY_PAUSE':
          if (state.isVideoPlaying) toggleVideoPlayback();
          else if (state.currentHeroItem) playVideo(state.currentHeroItem);
          break;
        case 'HOME':
          handleNavSelection('home', dom.sidebarNav.querySelector('#nav-home'));
          break;
      }
    };
  }

  // Run on DOM ready
  if (document.readyState === 'loading') {
    document.addEventListener('DOMContentLoaded', init);
  } else {
    init();
  }
})();
