import axios from "axios";

const DOMAIN = "http://localhost:8080";
const API_DOMAIN = `${DOMAIN}/api/v1`;

const ID_CHECK_URL = () => `${API_DOMAIN}/auth/id-check`;
const EMAIL_CERTIFICATION_URL = () => `${API_DOMAIN}/auth/email-certification`;
const CHECK_EMAIL_CERTIFICATION_URL = () => `${API_DOMAIN}/auth/check-email-certification`;
const SIGN_UP_URL = () => `${API_DOMAIN}/auth/signup`;
const LOG_IN_URL = () => `${API_DOMAIN}/auth/login`;

//아이디 중복확인
export const idCheckRequest = async (requestBody) => {
  const result = await axios.post(ID_CHECK_URL(), requestBody);
  return result;
};
//이메일 인증번호 전송
export const emailCertificationRequest = async (requestBody) => {
  const result = await axios.post(EMAIL_CERTIFICATION_URL(), requestBody);
  return result;
};
//인증번호 확인
export const checkEmailCertificationRequest = async (requestBody) => {
  const result = await axios.post(CHECK_EMAIL_CERTIFICATION_URL(), requestBody);
  return result;
};
//회원가입
export const signupRequest = async (requestBody) => {
  const result = await axios.post(SIGN_UP_URL(), requestBody);
  return result;
};
//로그인
export const loginRequest = async (requestBody) => {
  const result = await axios.post(LOG_IN_URL(), requestBody);
  return result;
};
