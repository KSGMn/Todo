import "../Header/Header.css";
import TextButton from "../../components/buttons/TextButton/TextButton";

const Header = () => {
  return (
    <div className="header-container">
      <a href="/" className="header-left">
        TodoList
      </a>
      <div className="header-right">
        <TextButton link={"login"} name={"로그인"} />
        <TextButton link={"signup"} name={"회원가입"} />
      </div>
    </div>
  );
};

export default Header;
