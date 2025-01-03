window.onload = function () {
    // 서버에서 전달된 Flash Attribute 값을 JavaScript 변수로 전달
    const alertMessage = /*[[${alertMessage}]]*/ "";
    if (alertMessage.trim() !== "") {
        alert(alertMessage); // 메시지 알림 표시
    }
};

let isDuplicateChecked = false;
let isPasswordChecked = false;
let isPasswordMatchChecked = false;

function checkIdDuplicate() { //아이디 중복확인
    const idInput = document.getElementById("id");
    if (!idInput || !idInput.value.trim()) {
        alert("아이디를 입력하세요.");
        return;
    }

    fetch(`/check-duplicate?id=${encodeURIComponent(idInput.value)}`, {
        method: "GET"
    })
        .then(response => {
            if (!response.ok) {
                throw new Error(`HTTP error! status: ${response.status}`);
            }
            return response.json(); // JSON으로 파싱
        })
        .then(data => {
            if (data.isDuplicate) {
                alert("이미 사용 중인 아이디입니다.");
                isDuplicateChecked = false;
            } else {
                alert("사용 가능한 아이디입니다.");
                isDuplicateChecked = true;
            }
        })
        .catch(error => {
            console.error("Error checking ID duplication:", error);
            alert("ID 중복 확인 중 오류가 발생했습니다.");
        });
}

function validatePassword() { //비밀번호 8자리 이상 확인
    const passwordInput = document.getElementById("password");
    const passwordError = document.getElementById("password-error");

    if (passwordInput.value.length < 8) {
        passwordError.style.display = "inline"; // 에러 메시지 표시
        isPasswordChecked = false;
    } else {
        passwordError.style.display = "none"; // 에러 메시지 숨김
        isPasswordChecked = true;
    }
}

function validatePasswordMatch() { //비밀번호 일치 확인
    const password = document.getElementById("password").value;
    const confirmPassword = document.getElementById("confirm-password").value;
    const matchMessage = document.getElementById("password-match-message");

    if (password === confirmPassword && password !== "") {
        matchMessage.textContent = "비밀번호가 일치합니다.";
        matchMessage.className = "success-message";
        matchMessage.style.display = "block";
        isPasswordMatchChecked = true;
    } else if (confirmPassword !== "") {
        matchMessage.textContent = "비밀번호가 일치하지 않습니다.";
        matchMessage.className = "error-message";
        matchMessage.style.display = "block";
        isPasswordMatchChecked = false;
    } else {
        matchMessage.style.display = "none"; // 확인란이 비어 있으면 메시지 숨김
    }
}

function validateForm(event) { //모두 확인시 회원가입 승인
    if (!isDuplicateChecked) {
        event.preventDefault(); // 폼 제출 막기
        alert("아이디 중복 확인을 해주세요.");
    }
    if (!isPasswordChecked) {
        event.preventDefault();
        alert("비밀번호는 8자리 이상이여야 합니다.");
    }
    if (!isPasswordMatchChecked) {
        event.preventDefault();
        alert("비밀번호가 일치하지 않습니다.")
    }
}
