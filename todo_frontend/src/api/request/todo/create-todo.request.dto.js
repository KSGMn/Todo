export function CreateTodoRequestDto(username, title, contents) {
  if (typeof username !== "string" && typeof title !== "string" && typeof contents !== "string")
    return Error("타입 불일치");
  return { username: username, title: title, contents: contents };
}
