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
    body: formData,
  })
    .then((response) => response.json())
    .then((data) => {
      console.log(data);

      showUploadImages(data);
    });
});

document.querySelector(".btn-primary").addEventListener("click", () => {});
