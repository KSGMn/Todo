export function IdCheckRequestDto(id) {
  if (typeof id !== "string") return Error("타입 불일치");
  return { userId: id };
}
