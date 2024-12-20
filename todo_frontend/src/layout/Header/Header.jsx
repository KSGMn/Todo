import "../Header/Header.css";
import TextButton from "../../components/buttons/TextButton/TextButton";
import { logoutRequest } from "../../api";

const Header = () => {
  const isLoggedIn = localStorage.getItem("isLoggedIn");
  const logout = () => {
    console.log("로그아웃");
    localStorage.removeItem("isLoggedIn");
    logoutRequest();
  };
  return (
    <div className="header-container">
      <a href="/" className="header-left">
        TodoList
      </a>
      <div className="header-right">
        {!isLoggedIn && <TextButton link={"login"} name={"로그인"} />}
        <TextButton
          link="signup"
          name={isLoggedIn ? "로그아웃" : "회원가입"}
          logout={isLoggedIn ? logout : undefined}
        />
      </div>
    </div>
  );
};

export default Header;
