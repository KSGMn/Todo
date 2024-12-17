import "../TextButton/TextButton.css";
import PropTypes from "prop-types";

const TextButton = ({ link, name }) => {
  return (
    <a href={link} className="menu__link">
      {name}
    </a>
  );
};

TextButton.propTypes = {
  link: PropTypes.string.isRequired,
  name: PropTypes.string.isRequired,
};

export default TextButton;
