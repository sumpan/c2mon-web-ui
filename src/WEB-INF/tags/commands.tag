<%@ attribute name="equipment" required="true" type="cern.c2mon.shared.common.process.EquipmentConfiguration"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>

<c:url var="commandviewer" value="../commandviewer" />

<c:choose>
  <c:when test="${fn:length(equipment.sourceCommandTags) == 0}">
    <div class="panel-body">
      <p>This Equipment has no Commands configured.</p>
    </div>
  </c:when>

  <c:otherwise>
    <div class="panel-body">
      <h3>Commands</h3>
      <span>Click on a Command to view its configuration.</span>
    </div>

    <table class="table table-bordered table-hover" style="border-collapse: collapse;">
      <thead>
        <tr>
          <th class="col-sm-2">ID</th>
          <th class="col-sm-7">Name</th>
          <th class="col-sm-3">Equipment</th>
        </tr>
      </thead>

      <tbody>
        <c:forEach items="${equipment.sourceCommandTags}" var="entry">
          <c:set var="command" value="${entry.value}"></c:set>

          <tr data-toggle="collapse" data-target="#collapseme-${command.id}" class="accordion-toggle clickable">
            <td>
              <a href="${commandviewer}/${command.id}">${command.id}</a>
            </td>
            <td>${command.name}</td>
            <td>${equipment.name}</td>
          </tr>

          <tr>
            <td colspan="5" class="hiddenRow">
              <div class="accordion-body collapse" id="collapseme-${command.id}">

                <table class="table table-striped table-bordered" style="margin-bottom: 0px;">
                  <thead>
                  </thead>
                  <tbody>
                    <tr>
                      <th class="col-sm-2">Source Timeout</th>
                      <td class="col-sm-10">${command.sourceTimeout}</td>
                    </tr>
                    <tr>
                      <th class="col-sm-2">Source Retries</th>
                      <td class="col-sm-10">${command.sourceRetries}</td>
                    </tr>
                    <tr>
                      <th class="col-sm-2">Hardware Address</th>
                      <td class="col-sm-10"><xmp>${fn:trim(fn:replace(command.hardwareAddress, '        ', ''))}</xmp></td>
                    </tr>
                  </tbody>
                </table>
              </div>
            </td>
          </tr>
        </c:forEach>
      </tbody>
    </table>

  </c:otherwise>
</c:choose>