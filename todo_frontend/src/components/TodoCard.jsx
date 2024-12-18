import PropTypes from "prop-types";

const TodoCard = ({ todos, handleDeleteTodo }) => {
  return (
    <div className="card p-3">
      <ul className="list-group list-group-flush">
        {todos.length === 0 ? (
          <li className="list-group-item text-center">등록된 Todo가 없습니다.</li>
        ) : (
          todos.map((todo) => (
            <li key={todo.id} className="list-group-item d-flex justify-content-between align-items-center gap-2">
              <div
                style={{
                  overflow: "hidden",
                  textOverflow: "ellipsis",
                  whiteSpace: "nowrap",
                  flex: 1,
                }}
              >
                <h5>{todo.title}</h5>
                <p
                  style={{
                    whiteSpace: "normal", // 줄바꿈 허용
                    WebkitLineClamp: 5, // 최대 5줄로 제한
                  }}
                >
                  {todo.content}
                </p>
                <small className="text-muted">생성일: {todo.createdAt}</small>
              </div>
              <div className="d-flex gap-2">
                <button className="btn btn-success btn-sm" onClick={() => handleDeleteTodo(todo.id)}>
                  완료
                </button>
                <button className="btn btn-danger btn-sm" onClick={() => handleDeleteTodo(todo.id)}>
                  삭제
                </button>
              </div>
            </li>
          ))
        )}
      </ul>
    </div>
  );
};

TodoCard.propTypes = {
  todos: PropTypes.arrayOf(
    PropTypes.shape({
      id: PropTypes.number.isRequired,
      title: PropTypes.string.isRequired,
      content: PropTypes.string,
      createdAt: PropTypes.string.isRequired,
    })
  ).isRequired,
  handleDeleteTodo: PropTypes.func.isRequired,
};

export default TodoCard;
