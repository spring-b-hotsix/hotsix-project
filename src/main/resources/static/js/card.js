const host = 'http://' + window.location.host;

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

// 카드 이름 수정 토글
function toggleName(state) {
    if (state === 'input') {
        document.getElementById('name-default').style.display = 'none';
        document.getElementById('name-input').style.display = 'flex';
    }
}

// 카드 이름 수정하기
function updateAndToggleName(state, boardId, sideId, cardId) {
    if (state === 'default') {
        let name = $('#card-name-input').val();
        let requestDto = {"name": name};

        fetch(`/boards/${boardId}/sides/${sideId}/cards/${cardId}/name`, {
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
                alert("이름 수정 완료!");

                // 모달 내용을 업데이트한 후 창을 새로고침
                location.reload();
                // toggleName('input')

                // Update description text and toggle back to default state
                $('#name-default .card-text').text(name);
                document.getElementById('name-default').style.display = 'block';
                document.getElementById('name-input').style.display = 'none';
                // // 모달 내용을 다시 불러와서 업데이트
                // refreshModalContent();
            });
    }
}

// 설명 수정 토글
function toggleDescription(state) {
    if (state === 'input') {
        document.getElementById('description-default').style.display = 'none';
        document.getElementById('description-input').style.display = 'block';
    }
}

// 설명 수정하기
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
                // toggleDescription('input')

                // Update description text and toggle back to default state
                $('#description-default .card-text').text(description);
                document.getElementById('description-default').style.display = 'block';
                document.getElementById('description-input').style.display = 'none';
                // // 모달 내용을 다시 불러와서 업데이트
                // refreshModalContent();
            });
    }
}

// 첨부파일 추가하기
function addAttachment(boardId, sideId, cardId) {
    let fileInput = document.getElementById('card-attachment-input');
    let file = fileInput.files[0];

    if (file) {
        let formData = new FormData();
        formData.append('file', file);

        fetch(`/boards/${boardId}/sides/${sideId}/cards/${cardId}/files`, {
            method: "POST",
            body: formData
        })
            .then(function (response) {
                return response.json();
            })
            .then(function (responseDto) {
                alert("파일 추가 완료!");

                // 모달 내용을 업데이트한 후 창을 새로고침
                location.reload();
            });
    }
}

// 첨부파일명 수정 토글
function toggleAttachmentName(state, fileId) {
    let attachmentDefaultId = "attachment-file-name-default-" + fileId;
    let attachmentupdateId = "attachment-file-name-update-" + fileId;
    if (state === 'input') {
        document.getElementById(attachmentDefaultId).style.display = 'none';
        document.getElementById(attachmentupdateId).style.display = 'flex';
    }
}

// 첨부파일명 수정하기
function updateAndtoggleAttachmentName(state, boardId, sideId, cardId, fileId) {
    if (state === 'default') {
        let attachmentInputId = "attachment-file-name-input-" + fileId;
        let fileName = $('#' + attachmentInputId).val();
        let requestDto = {"fileName": fileName};

        fetch(`/boards/${boardId}/sides/${sideId}/cards/${cardId}/files/${fileId}`, {
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
                alert("첨부 파일명 수정 완료!");

                // 모달 내용을 업데이트한 후 창을 새로고침
                location.reload();
                // toggleName('input')

                // Update description text and toggle back to default state
                $('#attachment-file-name-default-' + fileId + ' .card-text').text(fileName);
                document.getElementById('attachment-file-name-default').style.display = 'block';
                document.getElementById('attachment-file-name-update').style.display = 'none';
                // // 모달 내용을 다시 불러와서 업데이트
                // refreshModalContent();
            });
    }
}

// 첨부파일 삭제
function deleteAttachment(boardId, sideId, cardId, fileId) {
    fetch(`/boards/${boardId}/sides/${sideId}/cards/${cardId}/files/${fileId}`, {
        method: "DELETE",
        headers: {
            "Content-Type": "application/json"
        },
    })
        .then(function (response) {
            return response.json();
        })

        .then(function (responseDto) {
            alert("첨부 파일 삭제 완료!");

            // 모달 내용을 업데이트한 후 창을 새로고침
            location.reload();
            // toggleName('input')

            // // 모달 내용을 다시 불러와서 업데이트
            // refreshModalContent();
        });

}

// 체크리스트 생성
function addChecklist(boardId, sideId, cardId) {
    let name = $('#card-checklist-input').val();
    let requestDto = {"name": name};

    fetch(`/boards/${boardId}/sides/${sideId}/cards/${cardId}/checklists`, {
        method: "POST",
        headers: {
            "Content-Type": "application/json"
        },
        body: JSON.stringify(requestDto)
    })
        .then(function (response) {
            return response.json();
        })

        .then(function (responseDto) {
            alert(responseDto.msg);

            // 모달 내용을 업데이트한 후 창을 새로고침
            location.reload();
            // toggleDescription('input')

            // // 모달 내용을 다시 불러와서 업데이트
            // refreshModalContent();
        });
}

// 체크리스트 이름 수정 토글
function toggleChecklistName(state) {
    if (state === 'input') {
        document.getElementById('checklist-name-default').style.display = 'none';
        document.getElementById('checklist-name-input').style.display = 'flex';
    }
}

// 체크리스트 이름 수정하기
function updateAndToggleChecklistName(state, boardId, sideId, cardId, checklistId) {
    if (state === 'default') {
        let name = $('#checklist-name-update-input').val();
        let requestDto = {"name": name};

        fetch(`/boards/${boardId}/sides/${sideId}/cards/${cardId}/checklists/${checklistId}`, {
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
                alert(responseDto.msg);

                // 모달 내용을 업데이트한 후 창을 새로고침
                location.reload();
                // toggleName('input')

                // Update description text and toggle back to default state
                $('#checklist-name-default').text(name);
                document.getElementById('checklist-name-default').style.display = 'flex';
                document.getElementById('checklist-name-input').style.display = 'none';
                // // 모달 내용을 다시 불러와서 업데이트
                // refreshModalContent();
            });
    }
}

// 체크리스트 삭제
function deleteChecklist(boardId, sideId, cardId, checklistId) {
    fetch(`/boards/${boardId}/sides/${sideId}/cards/${cardId}/checklists/${checklistId}`, {
        method: "DELETE",
        headers: {
            "Content-Type": "application/json"
        }
    })
        .then(function (response) {
            return response.json();
        })

        .then(function (responseDto) {
            alert(responseDto.msg);

            // 모달 내용을 업데이트한 후 창을 새로고침
            location.reload();
            // toggleName('input')

            // // 모달 내용을 다시 불러와서 업데이트
            // refreshModalContent();
        });

}

// 체크리스트 아이템 생성
function addItem(boardId, sideId, cardId, checklistId) {
    let content = $('#card-checklist-item-input').val();
    let requestDto = {"content": content};

    fetch(`/boards/${boardId}/sides/${sideId}/cards/${cardId}/checklists/${checklistId}/item`, {
        method: "POST",
        headers: {
            "Content-Type": "application/json"
        },
        body: JSON.stringify(requestDto)
    })
        .then(function (response) {
            return response.json();
        })

        .then(function (responseDto) {
            alert(responseDto.msg);

            // 모달 내용을 업데이트한 후 창을 새로고침
            location.reload();
            // toggleDescription('input')

            // // 모달 내용을 다시 불러와서 업데이트
            // refreshModalContent();
        });
}

// 체크리스트 이름 수정하기
function updateCheckboxState(boardId, sideId, cardId, checklistId, itemId, isChecked) {
    fetch(`/boards/${boardId}/sides/${sideId}/cards/${cardId}/checklists/${checklistId}/item/${itemId}/check`, {
        method: "PUT",
        headers: {
            "Content-Type": "application/json"
        },
        body: JSON.stringify({ checked: isChecked })
    })
        .then(function (response) {
            return response.json();
        })

        .then(function (responseDto) {
            // 모달 내용을 업데이트한 후 창을 새로고침
            location.reload();
            // toggleName('input')

            // // 모달 내용을 다시 불러와서 업데이트
            // refreshModalContent();
        });
}

// 체크리스트 내용 수정 토글
function toggleItemContent(state) {
    if (state === 'input') {
        document.getElementById('item-content-default').style.display = 'none';
        document.getElementById('item-content-update').style.display = 'flex';
    }
}

// 체크리스트 내용 수정하기
function updateAndtoggleItemContent(state, boardId, sideId, cardId, checklistId, itemId, content) {
    if (state === 'default') {
        let content = $('#item-content-input').val();
        let requestDto = {"content": content};

        fetch(`/boards/${boardId}/sides/${sideId}/cards/${cardId}/checklists/${checklistId}/item/${itemId}/content`, {
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
                // 모달 내용을 업데이트한 후 창을 새로고침
                location.reload();
                // toggleName('input')

                // Update description text and toggle back to default state
                $('#item-content-default .card-text').text(name);
                document.getElementById('item-content-default').style.display = 'block';
                document.getElementById('item-content-update').style.display = 'none';
                // // 모달 내용을 다시 불러와서 업데이트
                // refreshModalContent();
            });
    }
}

// 아이템 삭제
function deleteItem(boardId, sideId, cardId, checklistId, itemId) {
    fetch(`/boards/${boardId}/sides/${sideId}/cards/${cardId}/checklists/${checklistId}/item/${itemId}`, {
        method: "DELETE",
        headers: {
            "Content-Type": "application/json"
        },
    })
        .then(function (response) {
            return response.json();
        })

        .then(function (responseDto) {
            alert(responseDto.msg);

            // 모달 내용을 업데이트한 후 창을 새로고침
            location.reload();
            // toggleName('input')

            // // 모달 내용을 다시 불러와서 업데이트
            // refreshModalContent();
        });

}

// 댓글 생성
function addComment(boardId, sideId, cardId) {
    let content = $('#card-comment-input').val();
    let requestDto = {"content": content};

    fetch(`/boards/${boardId}/sides/${sideId}/cards/${cardId}/comments`, {
        method: "POST",
        headers: {
            "Content-Type": "application/json"
        },
        body: JSON.stringify(requestDto)
    })
        .then(function (response) {
            return response.json();
        })

        .then(function (responseDto) {
            alert(responseDto.msg);

            // 모달 내용을 업데이트한 후 창을 새로고침
            location.reload();
            // toggleDescription('input')

            // // 모달 내용을 다시 불러와서 업데이트
            // refreshModalContent();
        });
}

// 댓글 수정 토글
function toggleComment(state) {
    if (state === 'input') {
        document.getElementById('comment-content-default').style.display = 'none';
        document.getElementById('comment-content-update').style.display = 'flex';
    }
}

// 댓글 수정하기
function updateAndToggleCommentContent(state, boardId, sideId, cardId, commentId) {
    if (state === 'default') {
        let content = $('#comment-content-input').val();
        let requestDto = {"content": content};

        fetch(`/boards/${boardId}/sides/${sideId}/cards/${cardId}/comments/${commentId}`, {
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
                alert("댓글 수정 완료!");

                // 모달 내용을 업데이트한 후 창을 새로고침
                location.reload();
                // toggleName('input')

                // Update description text and toggle back to default state
                $('#comment-content-default').text(content);
                document.getElementById('comment-content-default').style.display = 'flex';
                document.getElementById('comment-content-update').style.display = 'none';
                // // 모달 내용을 다시 불러와서 업데이트
                // refreshModalContent();
            });
    }
}

// 댓글 삭제
function deleteComment(boardId, sideId, cardId, commentId) {
    fetch(`/boards/${boardId}/sides/${sideId}/cards/${cardId}/comments/${commentId}`, {
        method: "DELETE",
        headers: {
            "Content-Type": "application/json"
        }
    })
        .then(function (response) {
            return response.json();
        })

        .then(function (responseDto) {
            alert(responseDto.msg);

            // 모달 내용을 업데이트한 후 창을 새로고침
            location.reload();
            // toggleName('input')

            // // 모달 내용을 다시 불러와서 업데이트
            // refreshModalContent();
        });

}

// 마감기한 삭제
function deleteDue(boardId, sideId, cardId) {
    fetch(`/boards/${boardId}/sides/${sideId}/cards/${cardId}/due-removal`, {
        method: "PUT",
        headers: {
            "Content-Type": "application/json"
        }
    })
        .then(function (response) {
            return response.json();
        })

        .then(function (responseDto) {
            alert("마감기한이 삭제되었습니다");

            // 모달 내용을 업데이트한 후 창을 새로고침
            location.reload();
            // toggleName('input')

            // // 모달 내용을 다시 불러와서 업데이트
            // refreshModalContent();
        });

}