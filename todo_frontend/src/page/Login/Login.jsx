import { useContext, useState } from "react";
import "../Login/Login.css";
import InputBox from "../../components/input-box/InputBox";
import { loginRequest } from "../../api";
import { useNavigate } from "react-router-dom";
import { AuthContext } from "../../provider/AuthProvider";
import Cookies from "js-cookie";

const Login = () => {
  const { setAccessToken } = useContext(AuthContext);
  const [id, setId] = useState("");
  const [password, setPassword] = useState("");
  const [message, setMessage] = useState("");
  const [isError, setIsError] = useState(false);

  const navigate = useNavigate();

  const handleChange = (event) => {
    const { name, value } = event.target;
    if (name === "id") {
      setId(value);
    }
    if (name === "password") {
      setPassword(value);
    }
  };

  // 회원가입 버튼 비활성화 조건
  const isDisabled = () => {
    // 필수 필드가 비어 있는지 확인
    const isEmpty = !id || !password;
    return isEmpty;
  };

  // ------------------------------------------- API요청

  //로그인 버튼
  const onLoginButtonClickHandler = (event) => {
    event.preventDefault();
    if (!id || !password) return;

    const LoginRequestDto = {
      userId: id,
      password: password,
    };

    loginRequest(LoginRequestDto)
      .then((data) => {
        const now = new Date().getTime();
        const expires = new Date(now + 900 * 1000); //15분
        const accessToken = data.data.accessToken;
        // const payload = data.data.accessToken.split(",")[1];
        // const decoded = JSON.parse(atob(payload));
        // console.log(decoded);
        const code = data.status;
        if (code === 200) {
          localStorage.setItem("isLoggedIn", "true");
          navigate("/");
          setAccessToken(accessToken);
          Cookies.set("accessToken", accessToken, { expires, path: "/" });
        }
      })
      .catch((data) => {
        try {
          const code = data.response.status;
          if (code === 400) {
            setIsError(true);
            setMessage("아이디 또는 비밀번호가 틀렸습니다.");
          }
        } catch (error) {
          alert("로그인에 실패하였습니다.");
          console.error(error);
        }
      });
  };

  return (
    <form method="POST" onSubmit={onLoginButtonClickHandler} className="login-form m-auto mt-5">
      <h2 className="h3-login">로그인</h2>
      <InputBox
        type={"text"}
        placeholder={"아이디를 입력하세요"}
        name={"id"}
        value={id}
        onChange={handleChange}
        isCheckButton={false}
      />

      <InputBox
        type={"password"}
        placeholder={"비밀번호를 입력하세요"}
        name={"password"}
        value={password}
        onChange={handleChange}
        isCheckButton={false}
        message={message}
        isError={isError}
      />

      <button className="login-submit-btn btn btn-secondary w-100 mt-5 p-2" type="submit" disabled={isDisabled()}>
        로그인
      </button>
    </form>
  );
};

export default Login;
