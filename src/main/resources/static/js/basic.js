const host = 'http://' + window.location.host;
let targetId;
let folderTargetId;

$(document).ready(function () {
    const auth = getToken();

    if (auth !== undefined && auth !== '') {
        $.ajaxPrefilter(function (options, originalOptions, jqXHR) {
            jqXHR.setRequestHeader('Authorization', auth);
        });
    } else {
        console.log("들어와지나")
        window.location.href = host + '/users/login-page';
        return;
    }

    $.ajax({
        type: 'GET',
        url: `/users/user-info`,
        contentType: 'application/json',
    })
        .done(function (res, status, xhr) {
            const nickname = res.nickname;
            const isAdmin = !!res.admin;

            if (!nickname) {
                window.location.href = '/users/login-page';
                return;
            }

            $('#nickname').text(nickname);
            if (isAdmin) {
                $('#admin').text(true);
                showProduct();
            } else {
                showProduct();
            }


        })
        .fail(function (jqXHR, textStatus) {
            logout();
        });

    // id 가 query 인 녀석 위에서 엔터를 누르면 execSearch() 함수를 실행하라는 뜻입니다.
    $('#query').on('keypress', function (e) {
        if (e.key == 'Enter') {
            execSearch();
        }
    });
    $('#close').on('click', function () {
        $('#container').removeClass('active');
    })
    $('#close2').on('click', function () {
        $('#container2').removeClass('active');
    })
    $('.nav div.nav-see').on('click', function () {
        $('div.nav-see').addClass('active');
        $('div.nav-search').removeClass('active');

        $('#see-area').show();
        $('#search-area').hide();
    })
    $('.nav div.nav-search').on('click', function () {
        $('div.nav-see').removeClass('active');
        $('div.nav-search').addClass('active');

        $('#see-area').hide();
        $('#search-area').show();
    })

    $('#see-area').show();
    $('#search-area').hide();
})

function numberWithCommas(x) {
    return x.toString().replace(/\B(?=(\d{3})+(?!\d))/g, ",");
}

function execSearch() {
    /**
     * 검색어 input id: query
     * 검색결과 목록: #search-result-box
     * 검색결과 HTML 만드는 함수: addHTML
     */
        // 1. 검색창의 입력값을 가져온다.
    let query = $('#query').val();

    // 2. 검색창 입력값을 검사하고, 입력하지 않았을 경우 focus.
    if (query == '') {
        alert('검색어를 입력해주세요');
        $('#query').focus();
        return;
    }
    // 3. GET /api/search?query=${query} 요청
    $.ajax({
        type: 'GET',
        url: `/api/search?query=${query}`,
        success: function (response) {
            $('#search-result-box').empty();
            // 4. for 문마다 itemDto를 꺼내서 HTML 만들고 검색결과 목록에 붙이기!
            for (let i = 0; i < response.length; i++) {
                let itemDto = response[i];
                let tempHtml = addHTML(itemDto);
                $('#search-result-box').append(tempHtml);
            }
        },
        error(error, status, request) {
            logout();
        }
    })

}

function addHTML(itemDto) {
    /**
     * class="search-itemDto" 인 녀석에서
     * image, title, lprice, addProduct 활용하기
     * 참고) onclick='addProduct(${JSON.stringify(itemDto)})'
     */
    return `<div class="search-itemDto">
        <div class="search-itemDto-left">
            <img src="${itemDto.image}" alt="">
        </div>
        <div class="search-itemDto-center">
            <div>${itemDto.title}</div>
            <div class="price">
                ${numberWithCommas(itemDto.lprice)}
                <span class="unit">원</span>
            </div>
        </div>
        <div class="search-itemDto-right">
            <img src="../images/icon-save.png" alt="" onclick='addProduct(${JSON.stringify(itemDto)})'>
        </div>
    </div>`
}

function addProduct(itemDto) {
    /**
     * modal 뜨게 하는 법: $('#container').addClass('active');
     * data를 ajax로 전달할 때는 두 가지가 매우 중요
     * 1. contentType: "application/json",
     * 2. data: JSON.stringify(itemDto),
     */

    // 1. POST /api/products 에 관심 상품 생성 요청
    $.ajax({
        type: 'POST',
        url: '/api/products',
        contentType: 'application/json',
        data: JSON.stringify(itemDto),
        success: function (response) {
            // 2. 응답 함수에서 modal을 뜨게 하고, targetId 를 reponse.id 로 설정
            $('#container').addClass('active');
            targetId = response.id;
        },
        error(error, status, request) {
            logout();
        }
    });
}

function showProduct(folderId = null) {
    /**
     * 관심상품 목록: #product-container
     * 검색결과 목록: #search-result-box
     * 관심상품 HTML 만드는 함수: addProductItem
     */

    let dataSource = null;

    var sorting = $("#sorting option:selected").val();
    var isAsc = $(':radio[name="isAsc"]:checked').val();

    if (folderId) {
        dataSource = `/api/folders/${folderId}/products?sortBy=${sorting}&isAsc=${isAsc}`;
    } else if(folderTargetId === undefined) {
        dataSource = `/api/products?sortBy=${sorting}&isAsc=${isAsc}&folderId=${folderId}`;
    } else {
        dataSource = `/api/folders/${folderTargetId}/products?sortBy=${sorting}&isAsc=${isAsc}`;
    }

    $('#product-container').empty();
    $('#search-result-box').empty();
    $('#pagination').pagination({
        dataSource,
        locator: 'content',
        alias: {
            pageNumber: 'page',
            pageSize: 'size'
        },
        totalNumberLocator: (response) => {
            return response.totalElements;
        },
        pageSize: 10,
        showPrevious: true,
        showNext: true,
        ajax: {
            beforeSend: function () {
                $('#product-container').html('상품 불러오는 중...');
            },
            error(error, status, request) {
                if (error.status === 403) {
                    $('html').html(error.responseText);
                    return;
                }
                logout();
            }
        },
        callback: function (response, pagination) {
            $('#product-container').empty();
            for (let i = 0; i < response.length; i++) {
                let product = response[i];
                let tempHtml = addProductItem(product);
                $('#product-container').append(tempHtml);
            }
        }
    });
}

// Folder 관련 기능
function openFolder(folderId) {
    folderTargetId = folderId;
    $("button.product-folder").removeClass("folder-active");
    if (!folderId) {
        $("button#folder-all").addClass('folder-active');
    } else {
        $(`button[value='${folderId}']`).addClass('folder-active');
    }
    showProduct(folderId);
}

// 폴더 추가 팝업
function openAddFolderPopup() {
    $('#container2').addClass('active');
}

// 폴더 Input 추가
function addFolderInput() {
    $('#folders-input').append(
        `<input type="text" class="folderToAdd" placeholder="추가할 폴더명">
       <span onclick="closeFolderInput(this)" style="margin-right:5px">
            <svg xmlns="http://www.w3.org/2000/svg" width="30px" fill="red" class="bi bi-x-circle-fill" viewBox="0 0 16 16">
              <path d="M16 8A8 8 0 1 1 0 8a8 8 0 0 1 16 0zM5.354 4.646a.5.5 0 1 0-.708.708L7.293 8l-2.647 2.646a.5.5 0 0 0 .708.708L8 8.707l2.646 2.647a.5.5 0 0 0 .708-.708L8.707 8l2.647-2.646a.5.5 0 0 0-.708-.708L8 7.293 5.354 4.646z"/>
            </svg>
       </span>
      `
    );
}

function closeFolderInput(folder) {
    $(folder).prev().remove();
    $(folder).next().remove();
    $(folder).remove();
}

function addFolder() {
    const folderNames = $('.folderToAdd').toArray().map(input => input.value);
    try {
        folderNames.forEach(name => {
            if (name === '') {
                alert('올바른 폴더명을 입력해주세요');
                throw new Error("stop loop");
            }
        });
    } catch (e) {
        console.log(e);
        return;
    }

    $.ajax({
        type: "POST",
        url: `/api/folders`,
        contentType: "application/json",
        data: JSON.stringify({
            folderNames
        })
    }).done(function (data, textStatus, xhr) {
        if(data !== '') {
            alert("중복된 폴더입니다.");
            return;
        }
        $('#container2').removeClass('active');
        alert('성공적으로 등록되었습니다.');
        window.location.reload();
    })
        .fail(function(xhr, textStatus, errorThrown) {
            alert(xhr.responseJSON.errorMessage);
            console.log(xhr.status)
        });
}

function addProductItem(product) {
    const folders = product.productFolderList.map(folder =>
        `
            <span onclick="openFolder(${folder.id})">
                #${folder.name}
            </span>
        `
    );
    return `<div class="product-card">
                <div onclick="window.location.href='${product.link}'">
                    <div class="card-header">
                        <img src="${product.image}"
                             alt="">
                    </div>
                    <div class="card-body">
                        <div class="title">
                            ${product.title}
                        </div>
                        <div class="lprice">
                            <span>${numberWithCommas(product.lprice)}</span>원
                        </div>
                        <div class="isgood ${product.lprice > product.myprice ? 'none' : ''}">
                            최저가
                        </div>
                    </div>
                </div>
                <div class="product-tags" style="margin-bottom: 20px;">
                    ${folders}
                    <span onclick="addInputForProductToFolder(${product.id}, this)">
                        <svg xmlns="http://www.w3.org/2000/svg" width="30px" fill="currentColor" class="bi bi-folder-plus" viewBox="0 0 16 16">
                            <path d="M.5 3l.04.87a1.99 1.99 0 0 0-.342 1.311l.637 7A2 2 0 0 0 2.826 14H9v-1H2.826a1 1 0 0 1-.995-.91l-.637-7A1 1 0 0 1 2.19 4h11.62a1 1 0 0 1 .996 1.09L14.54 8h1.005l.256-2.819A2 2 0 0 0 13.81 3H9.828a2 2 0 0 1-1.414-.586l-.828-.828A2 2 0 0 0 6.172 1H2.5a2 2 0 0 0-2 2zm5.672-1a1 1 0 0 1 .707.293L7.586 3H2.19c-.24 0-.47.042-.684.12L1.5 2.98a1 1 0 0 1 1-.98h3.672z"/>
                            <path d="M13.5 10a.5.5 0 0 1 .5.5V12h1.5a.5.5 0 0 1 0 1H14v1.5a.5.5 0 0 1-1 0V13h-1.5a.5.5 0 0 1 0-1H13v-1.5a.5.5 0 0 1 .5-.5z"/>
                        </svg>
                    </span>
                </div>
            </div>`;
}

function addInputForProductToFolder(productId, button) {
    $.ajax({
        type: 'GET',
        url: `/api/folders`,
        success: function (folders) {
            const options = folders.map(folder => `<option value="${folder.id}">${folder.name}</option>`)
            const form = `
                <span>
                    <form id="folder-select" method="post" autocomplete="off" action="/api/products/${productId}/folder">
                        <select name="folderId" form="folder-select">
                            ${options}
                        </select>
                        <input type="submit" value="추가" style="padding: 5px; font-size: 12px; margin-left: 5px;">
                    </form>
                </span>
            `;
            $(form).insertBefore(button);
            $(button).remove();
            $("#folder-select").on('submit', function (e) {
                e.preventDefault();
                $.ajax({
                    type: $(this).prop('method'),
                    url: $(this).prop('action'),
                    data: $(this).serialize(),
                }).done(function (data, textStatus, xhr) {
                    if(data !== '') {
                        alert("중복된 폴더입니다.");
                        return;
                    }
                    alert('성공적으로 등록되었습니다.');
                    window.location.reload();
                })
                    .fail(function(xhr, textStatus, errorThrown) {
                        alert(xhr.responseJSON.errorMessage);
                        console.log(xhr.status)
                    });
            });
        },
        error(error, status, request) {
            logout();
        }
    });
}

function setMyprice() {
    /**
     * 1. id가 myprice 인 input 태그에서 값을 가져온다.
     * 2. 만약 값을 입력하지 않았으면 alert를 띄우고 중단한다.
     * 3. PUT /api/product/${targetId} 에 data를 전달한다.
     *    주의) contentType: "application/json",
     *         data: JSON.stringify({myprice: myprice}),
     *         빠뜨리지 말 것!
     * 4. 모달을 종료한다. $('#container').removeClass('active');
     * 5, 성공적으로 등록되었음을 알리는 alert를 띄운다.
     * 6. 창을 새로고침한다. window.location.reload();
     */
        // 1. id가 myprice 인 input 태그에서 값을 가져온다.
    let myprice = $('#myprice').val();
    // 2. 만약 값을 입력하지 않았으면 alert를 띄우고 중단한다.
    if (myprice == '') {
        alert('올바른 가격을 입력해주세요');
        return;
    }

    // 3. PUT /api/product/${targetId} 에 data를 전달한다.
    $.ajax({
        type: 'PUT',
        url: `/api/products/${targetId}`,
        contentType: 'application/json',
        data: JSON.stringify({myprice: myprice}),
        success: function (response) {

            // 4. 모달을 종료한다. $('#container').removeClass('active');
            $('#container').removeClass('active');
            // 5. 성공적으로 등록되었음을 알리는 alert를 띄운다.
            alert('성공적으로 등록되었습니다.');
            // 6. 창을 새로고침한다. window.location.reload();
            window.location.reload();
        },
        error(error, status, request) {
            logout();
        }
    })
}

function logout() {
    // 토큰 삭제
    Cookies.remove('Authorization', {path: '/'});
    window.location.href = host + '/users/login-page';
}

function getToken() {

    let auth = Cookies.get('Authorization');

    if(auth === undefined) {
        return '';
    }

    // kakao 로그인 사용한 경우 Bearer 추가
    if(auth.indexOf('Bearer') === -1 && auth !== ''){
        auth = 'Bearer ' + auth;
    }

    return auth;
}