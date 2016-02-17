<div class="modal fade" id="modalMessage">
  <div class="modal-dialog modal-lg">
    <form class="form-horizontal" role="form" id="messageForm">
      <div class="modal-content modal_board">
        <div class="modal-header">
          <div class="container-fluid">
            <div class="form-group">
              <label for="messageTitle" class="col-xs-2 control-label">Title</label>

              <div class="col-xs-9">
                <input type="text" class="form-control " id="messageTitle" placeholder="title">
              </div>
              <div class="col-xs-1">
                <button class="close" type="button" id="closeModalTopButton">
                    <span class="fa fa-2x fa-times"></span>
                </button>
              </div>

            </div>
            <div class="row">
              <label for="messageCategory" class="col-xs-2 control-label">Category</label>

              <div class="col-xs-6">
                <select class="form-control" id="messageCategory">
                  <c:forEach var="category" items="${filterInSession.categoryMap}">
                    <option value="${category.key.idCategory}">${category.key.name}</option>
                  </c:forEach>
                </select>
              </div>
              <div class="col-xs-4">
                <button type="button" hidden id="messageIdMessage"></button>
              </div>
            </div>
          </div>
        </div>
        <div class="modal-header">
          <div class="container-fluid">
            <div class="row">
              <div class="col-xs-4 col-xs-offset-1" id="messageAuthor">
              </div>
              <div class="col-xs-3" id="messageCreated">
              </div>
              <div class="col-xs-3" id="messageUpdated">
              </div>
            </div>
          </div>
        </div>
        <div class="modal-body">
          <div class="container-fluid">
            <div class="form-group">
              <label for="messageBody" class="col-xs-2 control-label">Message</label>

              <div class="col-xs-9">
                                <textarea class="form-control" rows="8" placeholder="message"
                                          id="messageBody"></textarea>
              </div>
            </div>
          </div>
        </div>
        <div class="modal-footer">
          <div class="container-fluid">
            <div class="col-xs-4">
              <button class="btn btn-success" type="submit" id="saveMessage">
                Save message
              </button>
            </div>
            <div class="col-xs-4">
              <button class="btn btn-danger" type="button" id="deleteMessage">
                Delete message
              </button>
            </div>
            <div class="col-xs-4">
              <button class="btn btn-default" type="button" id="closeModalBottomButton">Close window</button>
            </div>
          </div>
        </div>
      </div>
    </form>
  </div>
</div>
