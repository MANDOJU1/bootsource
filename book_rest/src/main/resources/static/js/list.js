// 제목 클릭 시 a 태그 기능 중지
// data-id 에 있는 값 가져오기
document.querySelector("tbody").addEventListener("click", (e) => {
  e.preventDefault();
  const target = e.target;

  console.log(target.dataset.id);

  // id 를 이용해 한 개 데이터 가져오기
  // 변수 사용 ` `
  fetch(`http://localhost:8080/read/${target.dataset.id}`)
    .then((response) => response.json())
    .then((data) => {
      console.log(data);

      // 디자인영역 가져오기
      document.querySelector("#category").value = data.categoryName;
      document.querySelector("#title").value = data.title;
      document.querySelector("#publisher").value = data.publisherName;
      document.querySelector("#writer").value = data.writer;
      document.querySelector("#price").value = data.price;
      document.querySelector("#salePrice").value = data.salePrice;
      document.querySelector("#book_id").value = data.id;
    });
});
