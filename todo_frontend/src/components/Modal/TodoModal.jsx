import { useEffect, useState } from "react";
import { Modal, Button, Form } from "react-bootstrap";
import PropTypes from "prop-types";
import { useLocation, useOutletContext, useParams } from "react-router-dom";
import { createRecycleTodo, createTodo, updateTodo } from "../../api";

function TodoModal({ navigate, todos, setTodos }) {
  const { id } = useParams();
  const { showModal, setShowModal } = useOutletContext();
  const [title, setTitle] = useState("");
  const location = useLocation();
  const [contents, setContents] = useState("");

  const handleCloseModal = () => {
    setShowModal(false);
    navigate("/");
    return;
  };

  const findTodoById = (id) => {
    return todos.find((todo) => todo.id === parseInt(id));
  };

  useEffect(() => {
    if (location.pathname.includes("read")) {
      setTitle(findTodoById(id).title);
      setContents(findTodoById(id).contents);
      return;
    }
  }, []);

  const userId = localStorage.getItem("userId");

  // Todo 추가 api 요청
  const createTodoResponse = async () => {
    try {
      const CreateTodoRequestDto = { username: userId, title: title, contents: contents };
      return await createTodo(CreateTodoRequestDto);
    } catch (error) {
      console.log(error);
    }
  };

  // 반복할 Todo 추가 api 요청
  const createRecycleTodoResponse = async () => {
    try {
      const CreateTodoRequestDto = { username: userId, title: title, contents: contents };
      return await createRecycleTodo(CreateTodoRequestDto);
    } catch (error) {
      console.log(error);
    }
  };

  // Todo 수정 api 요청
  const updateTodoResponse = async () => {
    try {
      const CreateTodoRequestDto = { username: userId, title: title, contents: contents };
      return await updateTodo(CreateTodoRequestDto, id);
    } catch (error) {
      console.log(error);
    }
  };

  // Todo 추가
  const handleAddTodo = async () => {
    if (!title || !contents) {
      alert("제목과 내용을 입력하세요.");
      return;
    }

    const response = location.pathname.includes("/add2")
      ? await createRecycleTodoResponse()
      : await createTodoResponse();

    const newTodo = {
      id: response.data.data.id,
      username: response.data.data.username,
      title: response.data.data.title,
      contents: response.data.data.contents,
      targetDate: response.data.data.targetDate,
      recycle: response.data.data.recycle,
      done: response.data.data.done,
    };

    setTodos([...todos, newTodo]);
    setTitle("");
    setContents("");
    handleCloseModal();
  };

  // Todo 수정
  const handleUpdateTodo = async () => {
    if (!title || !contents) {
      alert("제목과 내용을 입력하세요.");
      return;
    }

    const response = await updateTodoResponse();

    const updatedTodo = {
      id: response.data.data.id,
      username: response.data.data.username,
      title: response.data.data.title,
      contents: response.data.data.contents,
      targetDate: response.data.data.targetDate,
      recycle: response.data.data.recycle,
      done: response.data.data.done,
    };

    // 기존 Todo 수정
    setTodos((prevTodos) => prevTodos.map((todo) => (todo.id === updatedTodo.id ? updatedTodo : todo)));
    setTitle("");
    setContents("");
    handleCloseModal();
  };

  return (
    <div className="container my-4">
      {/* 모달 컴포넌트 */}
      <Modal show={showModal} onHide={handleCloseModal}>
        <Modal.Header closeButton>
          <Modal.Title>
            {location.pathname === "/add2"
              ? "반복할 새 Todo 추가"
              : location.pathname === "/add/"
              ? "새 Todo 추가"
              : "Todo"}
          </Modal.Title>
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
                value={contents}
                onChange={(e) => setContents(e.target.value)}
              />
            </Form.Group>
          </Form>
        </Modal.Body>
        <Modal.Footer>
          <Button variant="secondary" onClick={handleCloseModal}>
            취소
          </Button>
          {location.pathname.includes("read") ? (
            <Button variant="primary" onClick={handleUpdateTodo}>
              수정
            </Button>
          ) : (
            <Button variant="primary" onClick={handleAddTodo}>
              생성
            </Button>
          )}
        </Modal.Footer>
      </Modal>
    </div>
  );
}

TodoModal.propTypes = {
  navigate: PropTypes.func.isRequired,
  todos: PropTypes.array.isRequired,
  setTodos: PropTypes.func.isRequired,
};

export default TodoModal;
