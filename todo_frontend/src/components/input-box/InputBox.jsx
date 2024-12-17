import "../input-box/InputBox.css";
import PropTypes from "prop-types";

const InputBox = ({
  type,
  placeholder,
  name,
  value,
  onChange,
  isCheckButton,
  onCheckButtonClickHandler,
  checkButtonName,
  message,
  isError,
  readOnly,
}) => {
  return (
    <div className="d-flex flex-column w-100">
      <div className="input-box-container">
        {type !== "textarea" ? (
          <input
            className={`input-box ${value ? "has-value" : ""}`}
            type={type}
            placeholder={placeholder}
            name={name}
            value={value || ""}
            onChange={onChange}
            required
            readOnly={readOnly || false}
          />
        ) : (
          <textarea
            className={`input-box text-area ${value ? "has-value" : ""}`}
            placeholder={placeholder}
            name={name}
            value={value || ""}
            onChange={onChange}
            required
            readOnly={readOnly || false}
          />
        )}
        <span className="input-box-border"></span>
        {isCheckButton && (
          <button
            type="button"
            onClick={onCheckButtonClickHandler}
            className={value ? "check-button" : "disable-check-button"}
          >
            {checkButtonName}
          </button>
        )}
      </div>
      {message && <div className={`${!isError ? "success-message" : "error-message"}`}>{message}</div>}
    </div>
  );
};

InputBox.propTypes = {
  type: PropTypes.string.isRequired,
  placeholder: PropTypes.string,
  name: PropTypes.string.isRequired,
  value: PropTypes.oneOfType([PropTypes.string, PropTypes.number]),
  onChange: PropTypes.func.isRequired,
  isCheckButton: PropTypes.bool,
  onCheckButtonClickHandler: PropTypes.func,
  checkButtonName: PropTypes.string,
  message: PropTypes.string,
  isError: PropTypes.bool,
  readOnly: PropTypes.bool,
};

export default InputBox;
