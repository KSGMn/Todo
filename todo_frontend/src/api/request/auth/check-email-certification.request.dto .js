export function CheckEmailCertificationRequestDto(id, email, cfNumber) {
  if (typeof id !== "string" && typeof email !== "string" && typeof cfNumber !== "string") return Error("타입 불일치");
  return { userId: id, email: email, certificationNumber: cfNumber };
}
