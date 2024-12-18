import { useState } from "react";
import { Modal, Button, Form } from "react-bootstrap";
import PropTypes from "prop-types";
import { useLocation, useOutletContext } from "react-router-dom";

function TodoModal({ navigate }) {
  const { showModal, setShowModal } = useOutletContext();
  const [todos, setTodos] = useState([]);
  const [title, setTitle] = useState("");
  const [content, setContent] = useState("");
  const location = useLocation();

  const handleCloseModal = () => {
    setShowModal(false);
    navigate("/");
    return;
  };

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
      {/* 모달 컴포넌트 */}
      <Modal show={showModal} onHide={handleCloseModal}>
        <Modal.Header closeButton>
          <Modal.Title>{location.pathname === "/add2" ? "반복할 새 Todo 추가" : "새 Todo 추가"}</Modal.Title>
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

TodoModal.propTypes = {
  navigate: PropTypes.func.isRequired,
};

export default TodoModal;
