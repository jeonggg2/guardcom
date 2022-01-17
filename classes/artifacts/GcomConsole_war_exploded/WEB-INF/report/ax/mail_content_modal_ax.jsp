<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<div id="mailDetailModal" class="modal fade" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true" >
	<div class="modal-dialog" style="width:900px" >
		<div class="modal-content">

			<!-- Modal Header -->
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
				<h4 class="modal-title" id="myModalLabel">${mail.subject}</h4>
			</div>

			<!-- Modal Body -->
			<div class="modal-body">
				<p><a href="#" data-toggle="tooltip" title="Tooltip">발신자</a> : ${mail.srcAddr}</p>
				<p><a href="#" data-toggle="tooltip" title="Tooltip">수신자</a> : ${mail.dstAddr}</p>

				<hr />
				<p><a href="#" data-toggle="tooltip" title="Tooltip">CC</a> : ${mail.cc}</p>
				<p><a href="#" data-toggle="tooltip" title="Tooltip">BCC</a> : ${mail.bcc}</p>

				<hr />
				<p><a href="#" data-toggle="tooltip" title="Tooltip">첨부파일</a> : ${mail.fileList}</p>

				<hr />

				<p>${mail.body}</p>
			</div>

			<!-- Modal Footer -->
			<div class="modal-footer">
				<button type="button" class="btn btn-default" data-dismiss="modal">확인</button>
			</div>

		</div>
	</div>
</div>