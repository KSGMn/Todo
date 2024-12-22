import axios from "axios";

const DOMAIN = "http://localhost:8080";
const API_DOMAIN = `${DOMAIN}/api/v1`;

// Axios 인스턴스 생성
const apiClient = axios.create({
  baseURL: API_DOMAIN, // 기본 API URL
  headers: {
    "Content-Type": "application/json",
  },
  withCredentials: true,
});

// 요청 인터셉터로 Authorization 헤더 추가
apiClient.interceptors.request.use(
  (config) => {
    const token = document.cookie
      .split("; ")
      .find((row) => row.startsWith("accessToken="))
      ?.split("=")[1];

    if (token) {
      config.headers.Authorization = `Bearer ${token}`; // 토큰 추가
    }
    return config;
  },
  (error) => Promise.reject(error)
);

const ID_CHECK_URL = () => `${API_DOMAIN}/auth/id-check`;
const EMAIL_CERTIFICATION_URL = () => `${API_DOMAIN}/auth/emails/certification`;
const CHECK_EMAIL_CERTIFICATION_URL = () => `${API_DOMAIN}/auth/emails/verification`;
const SIGN_UP_URL = () => `${API_DOMAIN}/auth/users`;
const LOG_IN_URL = () => `${API_DOMAIN}/auth/login`;
const LOG_OUT_URL = () => `${API_DOMAIN}/auth/logout`;
const FIND_ALL_TODO = () => `${API_DOMAIN}/todos`;
const CREATE_TODO = () => `${API_DOMAIN}/todos`;
const CREATE_RECYCLE_TODO = () => `${API_DOMAIN}/todos/add`;
const UPDATE_TODO = (id) => `${API_DOMAIN}/todos/${id}`;
const DONE_TODO = (id) => `${API_DOMAIN}/todos/done/${id}`;
const DELETE_TODO = (id) => `${API_DOMAIN}/todos/${id}`;

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

//로그아웃
export const logoutRequest = async (requestBody) => {
  const result = await axios.post(LOG_OUT_URL(requestBody), {}, { withCredentials: true });
  return result;
};

// 모든 Todo 조회
export const findAllTodo = async () => {
  const result = await apiClient.get(FIND_ALL_TODO());
  return result;
};

// Todo 생성
export const createTodo = async (requestBody) => {
  const result = await apiClient.post(CREATE_TODO(), requestBody);
  return result;
};

// 반복할 Todo 생성
export const createRecycleTodo = async (requestBody) => {
  const result = await apiClient.post(CREATE_RECYCLE_TODO(), requestBody);
  return result;
};

// Todo 업데이트
export const updateTodo = async (requestBody, id) => {
  const result = await apiClient.post(UPDATE_TODO(id), requestBody);
  return result;
};

// Todo 완료 및 취소
export const doneTodo = async (id) => {
  const result = await apiClient.post(DONE_TODO(id));
  return result;
};

// Todo 삭제
export const deleteTodo = async (id) => {
  const result = await apiClient.delete(DELETE_TODO(id));
  return result;
};
