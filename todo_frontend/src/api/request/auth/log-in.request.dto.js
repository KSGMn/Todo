export function LoginRequestDto(id, password) {
  if (typeof id !== "string" && typeof password !== "string") return Error("타입 불일치");
  return { userId: id, password: password };
}
