import "../TextButton/TextButton.css";
import PropTypes from "prop-types";

const TextButton = ({ link, name, logout, hidden }) => {
  const handleClick = (e) => {
    if (name === "로그아웃") {
      e.preventDefault(); // 기본 동작(링크 이동) 방지
      logout();
    }
  };

  return (
    <a
      href={link}
      className={`menu__link ${hidden ? "hidden" : ""}`}
      onClick={handleClick}
      style={hidden ? { visibility: "hidden" } : {}}
    >
      {name}
    </a>
  );
};

TextButton.propTypes = {
  link: PropTypes.string.isRequired,
  name: PropTypes.string.isRequired,
  logout: PropTypes.func,
  hidden: PropTypes.string,
};

export default TextButton;
