import { useEffect, useState } from "react";
import "../SignUp/SignUp.css";
import InputBox from "../../components/input-box/InputBox";
import { checkEmailCertificationRequest, emailCertificationRequest, idCheckRequest, signupRequest } from "../../api";
import { useNavigate } from "react-router-dom";

const SignUp = () => {
  const [id, setId] = useState("");
  const [email, setEmail] = useState("");
  const [cfNumber, setCfNumber] = useState("");
  const [name, setName] = useState("");
  const [password, setPassword] = useState("");
  const [confirmPassword, setConfirmPassword] = useState("");
  const [idMessage, setIdMessage] = useState("");
  const [emailMessage, setEmailMessage] = useState("");
  const [cfNumberMessage, setCfNumberMessage] = useState("");
  const [passwordMessage, setPasswordMessage] = useState("");
  const [confirmPasswordMessage, setConfirmPasswordMessage] = useState("");
  const [idError, setIdError] = useState(false);
  const [emailError, setEmailError] = useState(false);
  const [cfNumberError, setCfNumberError] = useState(false);
  const [nameError] = useState(false);
  const [passwordError, setPasswordError] = useState(false);
  const [confirmPasswordError, setConfirmPasswordError] = useState(false);
  const [verifiedId, setVerifiedId] = useState(false);
  const [verifiedCfNumber, setVerifiedCfNumber] = useState(false);

  const navigate = useNavigate();

  const emailPattern = /^[a-zA-Z0-9]*@([-.]?[a-zA-Z0-9])*\.[a-zA-Z]{2,4}$/;

  const handleChange = (event) => {
    const { name, value } = event.target;
    if (name === "id") {
      setId(value);
    }
    if (name === "email") {
      setEmail(value);
    }
    if (name === "cfNumber") {
      setCfNumber(value);
    }
    if (name === "name") {
      setName(value);
    }
    if (name === "password") {
      setPassword(value);
    }
    if (name === "confirmPassword") {
      setConfirmPassword(value);
    }
  };

  //비밀번호 유효성 검사
  useEffect(() => {
    const passwordPattern = /^(?=.*[a-z])(?=.*[A-Z])(?=.*[0-9])(?=.*[@$!%*?&])[A-Za-z0-9@$!%*?&]{8,}$/;

    const passwordValid = password && passwordPattern.test(password);
    const passwordsMatch = password && confirmPassword && password === confirmPassword;

    setPasswordError(!passwordValid);
    setConfirmPasswordError(confirmPassword ? !passwordsMatch : false);

    setPasswordMessage(
      password
        ? passwordValid
          ? ""
          : "비밀번호는 소문자와 대문자, 숫자, 특수문자를 포함한 8자 이상이어야 합니다."
        : ""
    );
    setConfirmPasswordMessage(confirmPassword ? (passwordsMatch ? "" : "비밀번호가 일치하지 않습니다.") : "");
  }, [password, confirmPassword]);

  // 회원가입 버튼 비활성화 조건
  const isDisabled = () => {
    // 필수 필드가 비어 있는지 확인
    const isEmpty = !id || !email || !name || !password || !confirmPassword;

    // // 어떤 에러 메시지도 있으면 안 됨
    const hasErrors = [idError, emailError, cfNumberError, nameError, passwordError, confirmPasswordError].some(
      (error) => error
    );

    // // 이메일과 아이디가 검증되었는지 확인
    const notVerified = !verifiedId || !verifiedCfNumber;

    return isEmpty || hasErrors || notVerified;
  };

  // ------------------------------------------- API요청

  //아이디 중복확인 버튼
  const onIdCheckButtonClickHandler = () => {
    if (!id) return;
    const IdCheckRequestDto = { userId: id };

    idCheckRequest(IdCheckRequestDto)
      .then((data) => {
        const code = data.status;
        if (code === 200) {
          setIdError(false);
          setIdMessage("사용 가능한 아이디 입니다.");
          setVerifiedId(true);
        }
      })
      .catch((data) => {
        const code = data.response.status;
        if (code === 409) {
          setIdError(true);
          setIdMessage("중복된 아이디 입니다.");
        } else {
          alert("데이터베이스 오류입니다.");
        }
      });
  };

  //이메일 인증번호 전송 버튼
  const onEmialCertificationClickHandler = () => {
    if (!id || !email) return;

    const checkedEmail = emailPattern.test(email);

    if (!checkedEmail) {
      setEmailError(true);
      setEmailMessage("올바른 이메일 형식이 아닙니다.");
      return;
    }
    const EmailCertificationRequestDto = { userId: id, email: email };

    emailCertificationRequest(EmailCertificationRequestDto)
      .then((data) => {
        const code = data.status;
        if (code === 200) {
          setEmailError(false);
          setEmailMessage("인증번호가 전송되었습니다.");
        }
      })
      .catch((data) => {
        try {
          const code = data.response.status;
          if (code === 409) {
            setEmailError(true);
            setEmailMessage("중복된 이메일 입니다.");
          } else if (code === 400) {
            setEmailError(true);
            setEmailMessage("이메일 전송 실패.");
          }
        } catch (error) {
          alert("데이터베이스 오류입니다.");
          console.error(error);
        }
      });
  };

  //인증번호 확인 버튼
  const onCheckEmailCertificationClickHandler = () => {
    if (!id || !email || !cfNumber) return;

    const CheckEmailCertificationRequestDto = { userId: id, email: email, certificationNumber: cfNumber };

    checkEmailCertificationRequest(CheckEmailCertificationRequestDto)
      .then((data) => {
        const code = data.status;
        if (code === 200) {
          setEmailMessage("인증이 완료되었습니다.");
          setCfNumberError(false);
          setCfNumberMessage("인증이 완료되었습니다.");
          setVerifiedCfNumber(true);
        }
      })
      .catch((data) => {
        try {
          const code = data.response.status;
          if (code === 401) {
            setCfNumberError(true);
            setCfNumberMessage("인증에 실패하였습니다.");
          }
        } catch (error) {
          alert("데이터베이스 오류입니다.");
          console.error(error);
        }
      });
  };
  //회원가입 버튼
  const onSignUpButtonClickHandler = (event) => {
    event.preventDefault();
    if (!id || !email || !cfNumber || !name || !password || !confirmPassword) return;

    const SignupRequestDto = {
      userId: id,
      password: password,
      email: email,
      userName: name,
      certificationNumber: cfNumber,
    };

    signupRequest(SignupRequestDto)
      .then((data) => {
        const code = data.status;
        if (code === 200) {
          alert("회원가입에 성공하였습니다.");
          navigate("/login");
        }
      })
      .catch((data) => {
        alert("회원가입에 실패하였습니다.");
        console.error(data);
      });
  };

  return (
    <form method="POST" onSubmit={onSignUpButtonClickHandler} className="signup-form m-auto mt-5">
      <h2 className="h3-signup">회원가입</h2>
      <InputBox
        type={"text"}
        placeholder={"아이디를 입력하세요"}
        name={"id"}
        value={id}
        onChange={handleChange}
        isCheckButton={true}
        onCheckButtonClickHandler={() => onIdCheckButtonClickHandler()}
        checkButtonName={"중복확인"}
        message={idMessage}
        isError={idError}
      />
      <InputBox
        type={"email"}
        placeholder={"이메일을 입력하세요"}
        name={"email"}
        value={email}
        onChange={handleChange}
        isCheckButton={true}
        onCheckButtonClickHandler={() => onEmialCertificationClickHandler()}
        checkButtonName={"인증요청"}
        message={emailMessage}
        isError={emailError}
      />

      <InputBox
        type={"text"}
        placeholder={"인증번호를 입력하세요"}
        name={"cfNumber"}
        value={cfNumber}
        onChange={handleChange}
        isCheckButton={true}
        onCheckButtonClickHandler={() => onCheckEmailCertificationClickHandler()}
        checkButtonName={"인증하기"}
        message={cfNumberMessage}
        isError={cfNumberError}
      />
      <InputBox
        type={"text"}
        placeholder={"이름을 입력하세요"}
        name={"name"}
        value={name}
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
        message={passwordMessage}
        isError={passwordError}
      />
      <InputBox
        type={"password"}
        placeholder={"비밀번호를 다시 입력하세요"}
        name={"confirmPassword"}
        value={confirmPassword}
        onChange={handleChange}
        isCheckButton={false}
        message={confirmPasswordMessage}
        isError={confirmPasswordError}
      />
      <button className="signup-submit-btn btn btn-primary w-100 mt-5 p-2" type="submit" disabled={isDisabled()}>
        가입하기
      </button>
    </form>
  );
};

export default SignUp;
