import "../Header/Header.css";
import TextButton from "../../components/buttons/TextButton/TextButton";
import { logoutRequest } from "../../api";
import PropTypes from "prop-types";

const Header = ({ isLoggedIn, setIsLoggedIn }) => {
  const logout = () => {
    localStorage.removeItem("isLoggedIn");
    setIsLoggedIn(false);
    logoutRequest();
    window.location.reload();
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

Header.propTypes = {
  isLoggedIn: PropTypes.bool.isRequired,
  setIsLoggedIn: PropTypes.func,
};

export default Header;
