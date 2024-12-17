import { useState } from "react";
import { Modal, Button, Form } from "react-bootstrap";

function TodoModal() {
  const [todos, setTodos] = useState([]);
  const [showModal, setShowModal] = useState(false);
  const [title, setTitle] = useState("");
  const [content, setContent] = useState("");

  // 모달 열기/닫기
  const handleShowModal = () => setShowModal(true);
  const handleCloseModal = () => setShowModal(false);

  // Todo 추가
  const handleAddTodo = () => {
    if (!title || !content) {
      alert("제목과 내용을 입력하세요.");
      return;
    }

    const newTodo = {
      id: Date.now(),
      title,
      content,
      createdAt: new Date().toLocaleString(),
    };

    setTodos([...todos, newTodo]); // Todo 리스트 업데이트
    setTitle(""); // 입력 필드 초기화
    setContent("");
    handleCloseModal(); // 모달 닫기
  };

  return (
    <div className="container my-4">
      <h1 className="text-center mb-4">Todo List</h1>

      {/* Todo 생성 버튼 */}
      <Button variant="primary" onClick={handleShowModal}>
        새 Todo 추가
      </Button>

      {/* Todo 리스트 */}
      <div className="card mt-4">
        <ul className="list-group list-group-flush">
          {todos.length === 0 ? (
            <li className="list-group-item text-center">등록된 Todo가 없습니다.</li>
          ) : (
            todos.map((todo) => (
              <li key={todo.id} className="list-group-item d-flex justify-content-between align-items-center">
                <div>
                  <h5>{todo.title}</h5>
                  <p>{todo.content}</p>
                  <small className="text-muted">생성일: {todo.createdAt}</small>
                </div>
                <Button variant="danger" size="sm" onClick={() => setTodos(todos.filter((t) => t.id !== todo.id))}>
                  삭제
                </Button>
              </li>
            ))
          )}
        </ul>
      </div>

      {/* 모달 컴포넌트 */}
      <Modal show={showModal} onHide={handleCloseModal}>
        <Modal.Header closeButton>
          <Modal.Title>새 Todo 추가</Modal.Title>
        </Modal.Header>
        <Modal.Body>
          <Form>
            <Form.Group className="mb-3" controlId="formTitle">
              <Form.Label>제목</Form.Label>
              <Form.Control
                type="text"
                placeholder="제목을 입력하세요"
                value={title}
                onChange={(e) => setTitle(e.target.value)}
              />
            </Form.Group>
            <Form.Group className="mb-3" controlId="formContent">
              <Form.Label>내용</Form.Label>
              <Form.Control
                as="textarea"
                rows={3}
                placeholder="내용을 입력하세요"
                value={content}
                onChange={(e) => setContent(e.target.value)}
              />
            </Form.Group>
          </Form>
        </Modal.Body>
        <Modal.Footer>
          <Button variant="secondary" onClick={handleCloseModal}>
            취소
          </Button>
          <Button variant="primary" onClick={handleAddTodo}>
            생성
          </Button>
        </Modal.Footer>
      </Modal>
    </div>
  );
}

export default TodoModal;
