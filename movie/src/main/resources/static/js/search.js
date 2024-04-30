// 검색 창에서 엔터를 치면 이벤트 발생
// 검색어 가지고 온 후 searchForm 안 keyword 요소 value 값 지정
// searchForm 전송
document.querySelector("[name='keyword']").addEventListener("keyup", (e) => {
  if (e.keyCode == 13) {
    // alert("엔터");
    // 검색어 가져오기
    const keyword = e.target.value;
    if (!keyword) {
      alert("검색어를 입력해 주세요");
      return;
    }

    const searchForm = document.querySelector("#searchForm");
    searchForm.querySelector("[name='keyword']").value = keyword;

    console.log(searchForm);

    searchForm.submit();
  }
});
