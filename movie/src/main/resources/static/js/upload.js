// fileInput 찾기
const fileInput = document.querySelector("#fileInput");

// 업로드 파일 보여주기 찾기
const uploadResult = document.querySelector(".uploadResult ul");

function checkExtension(fileName) {
  // 정규식 사용
  const regex = /(.*?).(png|gif|jpg)$/;

  console.log(regex.test(fileName));
  return regex.test(fileName);
}

function showUploadImages(arr) {
  console.log("showUploadImages ", arr);

  const output = document.querySelector("#output");

  let tags = "";

  arr.forEach((obj, idx) => {
    // tags += `<div>`;
    // tags += `<img src = "display?fileName=${element.thumbImageURL}">`;
    // tags += `<a href="${element.imageURL}"><i class="fa-solid fa-xmark"></i></a>`;
    // tags += `</div>`;

    tags += `<li data-name="${obj.fileName}" data-path="${obj.folderPath}" data-uuid="${obj.uuid}">`;
    tags += `<div>`;
    tags += `<a href=""><img src="/upload/display?fileName=${obj.thumbImageURL}" class="block"></a>`;
    tags += `<span class="text-sm d-inline-block mx-1">${obj.fileName}</span>`;
    tags += `<a href="#" data-file="${obj.imageURL}">`;
    tags += `<i class="fa-solid fa-xmark"></i>`;
    tags += `</a></div></li>`;
  });
  uploadResult.insertAdjacentHTML("beforeend", tags);
}

// fileInput change 이벤트
fileInput.addEventListener("change", (e) => {
  // checkExtension() 호출
  // 이미지 파일이라면 FormData() 객체 생성 후 append 하기

  const files = e.target.files;

  const formData = new FormData();

  for (let index = 0; index < files.length; index++) {
    if (checkExtension(files[index].name)) {
      formData.append("uploadFiles", files[index]);
    }
  }

  for (const value of formData.values()) {
    console.log(value);
  }

  fetch("/upload/uploadAjax", {
    method: "post",
    headers: {
      "X-CSRF-TOKEN": csrfValue,
    },
    body: formData,
  })
    .then((response) => response.json())
    .then((data) => {
      console.log(data);

      showUploadImages(data);
    });
});

// register, modify 중복 사용
// form submit 기능 중지
// uploadResult ul li 태그 요소 가져오기
document.querySelector("#register-form").addEventListener("submit", (e) => {
  e.preventDefault();

  const form = e.target;

  // 첨부파일 정보 수집
  const attachInfos = document.querySelectorAll(".uploadResult ul li");
  console.log(attachInfos);

  // 수집된 정보를 폼 태그 자식으로 붙여넣기 (form안에 첨부파일이랑 함께 보내기 위해)
  let result = "";
  attachInfos.forEach((obj, idx) => {
    // hidden 3개 => MovieImageDto 객체 하나로 변경
    result += `<input type='hidden' value='${obj.dataset.path}' name='movieImageDtos[${idx}].path'>`;
    result += `<input type='hidden' value='${obj.dataset.uuid}' name='movieImageDtos[${idx}].uuid'>`;
    result += `<input type='hidden' value='${obj.dataset.name}' name='movieImageDtos[${idx}].imgName'>`;
  });
  form.insertAdjacentHTML("beforeend", result);

  console.log(form.innerHTML);

  form.submit();
});
