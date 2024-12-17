export function EmailCertificationRequestDto(id, email) {
  if (typeof id !== "string" && typeof email !== "string") return Error("타입 불일치");
  return { userId: id, email: email };
}
