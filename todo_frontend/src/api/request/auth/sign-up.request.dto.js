export function SignupRequestDto(id, email, password, name, cfNumber) {
  if (
    typeof id !== "string" &&
    typeof email !== "string" &&
    typeof cfNumber !== "string" &&
    typeof name !== "string" &&
    typeof password !== "string"
  )
    return Error("타입 불일치");
  return { userId: id, password: password, email: email, userName: name, certificationNumber: cfNumber };
}
