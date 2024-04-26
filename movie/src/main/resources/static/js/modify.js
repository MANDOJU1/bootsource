// 삭제 버튼 클릭 시 actionForm 보내기
document.querySelector(".btn-danger").addEventListener("click", (e) => {
  const actionForm = document.querySelector("#actionForm");

  if (!confirm("삭제하시겠습니까?")) {
    return;
  }

  actionForm.submit();
});
