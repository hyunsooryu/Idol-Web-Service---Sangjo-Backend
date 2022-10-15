(function () {
    var isLogin = renderJson.isLogin;
    if(isLogin){
        $("#icon").attr("src", "/img/upload.png");
        $("#loginForm").hide();
        $("#fileUploadForm").show();

    }else{
        $("#icon").attr("src","/img/gsshop.png");
        $("#loginForm").show();
        $("#fileUploadForm").hide();
        document.getElementById("login").value = renderJson.id;
        document.getElementById("btn1").addEventListener("click", function(){

            let id = renderJson.id;
            let pw = document.getElementById("password").value;

            axios.post('/admin/login', {
                id: id,
                pw: pw
            })
                .then(function (response) {
                    console.log(response);
                    swal("환영해요 상조님!", "오늘도 즐거운하루되세요!!", "success").then(()=>{
                        window.location.reload();
                    });
                })
                .catch(function (error) {
                    swal("에러발생", error.response.data.msg, "error").then(()=>{
                        document.getElementById("password").value = "";
                    });
                });
        });


    }
})();
