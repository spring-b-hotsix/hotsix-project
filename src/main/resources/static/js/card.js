const host = 'http://' + window.location.host;

function toggleDescription(state) {
    if (state === 'input') {
        document.getElementById('description-default').style.display = 'none';
        document.getElementById('description-input').style.display = 'block';
    }
}

function updateAndToggleDescription(state, boardId, sideId, cardId) {
    if (state === 'default') {
        let description = $('#card-description-input').val();
        let requestDto = {"description": description};

        fetch(`/boards/${boardId}/sides/${sideId}/cards/${cardId}/desc`, {
            method: "PUT",
            headers: {
                "Content-Type": "application/json"
            },
            body: JSON.stringify(requestDto)
        })
            .then(function (response) {
                return response.json();
            })

            .then(function (responseDto) {
                alert("설명 수정 완료!");

                // 모달 내용을 업데이트한 후 창을 새로고침
                location.reload();
                toggleDescription('input')

                // Update description text and toggle back to default state
                $('#description-default .card-text').text(description);
                document.getElementById('description-default').style.display = 'block';
                document.getElementById('description-input').style.display = 'none';
                // // 모달 내용을 다시 불러와서 업데이트
                // refreshModalContent();
            });
    }
}

// function refreshModalContent() {
//     // 모달 내용을 불러오는 AJAX 요청
//     fetch('url_to_fetch_modal_content')
//         .then(function (response) {
//             return response.text();
//         })
//         .then(function (modalHtml) {
//             // 모달 컨테이너에 새로운 모달 내용 업데이트
//             document.getElementById('modalContentContainer').innerHTML = modalHtml;
//         });
// }