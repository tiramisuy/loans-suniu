<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<table width="100%" border="0" cellpadding="0" cellspacing="0">
		<tr>
			<td align="center" class="pn-sp">
				共${page.totalCount}条&nbsp; 每页${page.pageSize}条&nbsp;
				<input type="button" class="comm-btn" value="首 页" onclick="jumpPage('1');"/> 
				<input type="button" class="comm-btn" value="上一页" onclick="jumpPage('${page.prePage}');"  ${page.hasPre eq true?'':'disabled=disabled' }/>
				<input type="button" class="comm-btn" value="下一页" onclick="jumpPage('${page.nextPage}');" ${page.hasNext eq true?'':'disabled=disabled' }/> 
				<input type="button" class="comm-btn" value="尾 页" onclick="jumpPage('${page.totalPages}');"/>&nbsp; 当前 ${page.pageNo}/${page.totalPages} 页&nbsp;转到第
				<input type="text" id="goto" style="width:30px"  value="${page.pageNo}"/>页
				<input type="button" class="comm-btn" value="转" onclick="gotoPage($('#goto').val(),'${page.totalPages}');" />
			</td>
		</tr>
</table>