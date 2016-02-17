<div class="board board_red">
    <div class="board_header"><h3>Filter <span class="fa fa-filter"></span></h3></div>
    <div class="board_message">
        <form class="form-horizontal" role="form" id="filterForm">

            <h4>Author</h4>

            <div class="container-fluid">
                <div class="checkbox ">
                    <label>
                        <input type="checkbox" id="onlyMyMessageCheckBox" disabled onclick="onlyMyMessageClick()"
                        <c:if test="${filterInSession.onlyMyMessage}"> checked </c:if>
                                >
                        show only my message
                    </label>
                </div>

                <div class="form-group">
                    <div class="col-xs-12">
                        <input type="text" class="form-control" id="inputAuthor" placeholder="author"
                               value="${filterInSession.authorName}"
                        <c:if test="${filterInSession.onlyMyMessage}"> disabled </c:if>
                                >
                    </div>
                </div>
            </div>

            <h4>Contains ...</h4>

            <div class="container-fluid">
                <div class="form-group">
                    <label for="inputTitle" class="col-xs-4 control-label">title</label>

                    <div class="col-xs-8">
                        <input type="text" class="form-control" id="inputTitle"
                               placeholder="title" value="${filterInSession.partOfTitle}">
                    </div>
                </div>
                <div class="form-group">
                    <label for="inputMessage" class="col-xs-4 control-label">message</label>

                    <div class="col-xs-8">
                        <input type="text" class="form-control" id="inputMessage"
                               placeholder="message" value="${filterInSession.partOfMessage}">
                    </div>
                </div>
            </div>
            <h4>Category</h4>

            <div class="board_message">

                <div class="container-fluid" id="categoryCheckBoxGroup">
                    <c:forEach var="category" items="${filterInSession.categoryMap}">
                        <div class="checkbox ">
                            <label>
                                <input type="checkbox" value="${category.key.idCategory}"
                                <c:if test="${category.value}"> checked </c:if> >
                                    ${category.key.name}
                            </label>
                        </div>

                    </c:forEach>
                </div>
            </div>
            <br/>

            <div class="col-xs-12">
                <button type="submit" class="btn btn-success btn-block" id="filterSubmitButton">
                    apply filter <span class="fa fa-filter"></span>
                </button>
            </div>

        </form>
    </div>
</div>