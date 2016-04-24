<%@ include file="/apps/aemsolrsearch/components/global.jspx" %>
<%@ taglib prefix="cqsearch" uri="http://aemsolrsearch.headwire.com/taglibs/aemsolrsearch-taglib" %>

<%--
  | Note: If you wish to extend this JSP, only include valid JavaScript in the response, as this
  |       JSP is included by searchcontroller.jsp.
  --%>

<c:if test="${resultsEnabled}">

    AjaxSolr.ResultWidget = AjaxSolr.AbstractWidget.extend({
      start: 0,

      beforeRequest: function () {
        $(this.target).html($('<img>').attr('src', '/etc/clientlibs/aemsolrsearch/ajax-solr/images/ajax-loader.gif'));
      },

      facetLinks: function (facet_field, facet_values) {
        var links = [];
        if (facet_values) {
          for (var i = 0, l = facet_values.length; i < l; i++) {
            if (facet_values[i] !== undefined) {
              links.push(
                $('<a href="#"></a>')
                .text(facet_values[i])
                .click(this.facetHandler(facet_field, facet_values[i]))
              );
            }
            else {
              <%-- links.push('no items found in current selection'); --%>
            }
          }
        }
        return links;
      },

      facetHandler: function (facet_field, facet_value) {
        var self = this;
        return function () {
          self.manager.store.remove('fq');
          self.manager.store.addByValue('fq', facet_field + ':' + AjaxSolr.Parameter.escapeValue(facet_value));
          self.doRequest();
          return false;
        };
      },

      afterRequest: function () {
        $(this.target).empty();
        for (var i = 0, l = this.manager.response.response.docs.length; i < l; i++) {
          var doc = this.manager.response.response.docs[i];
          $(this.target).append(this.template(doc));

        }
      },

      template: function (doc) {
        var snippet = '';

        if( typeof doc.teaser != 'undefined') {
            if(doc.teaser.length > 250)
            {
                snippet += doc.teaser.substring(0, 250);
                snippet += '<span style="display:none">' + doc.teaser.substring(250);
                snippet += '</span> <a href="#" class="more">more</a>';
            }
            else
            {
                snippet += doc.teaser;
            }
        }

        var title = doc.name;
        var pdpUrl = '/content/aemsolrsearch-product-sample/en/product.' + doc.id + '.html';
        var output = '';

        if(this.manager.response.highlighting && this.manager.response.highlighting[doc.id]) {
            if(this.manager.response.highlighting[doc.id]['teaser']) {
                var teaserSnippet = this.manager.response.highlighting[doc.id]['teaser'][0];

                if (teaserSnippet.length > 250) {
                    snippet = teaserSnippet.substring(0, 250);
                    snippet += '<span style="display:none">' + teaserSnippet.substring(250);
                    snippet += '</span> <a href="#" class="more">more</a>';
                } else {
                    snippet = teaserSnippet;
                }
            }
        }

        var thumbnail = doc.imageSmall_url;

        output += '<div class="result-card">';

          output += '<div class="media">';
            output += '<div class="media-left">';
              output += '<a href="#">';
                output += '<img class="media-object" src="' + thumbnail + '">';
              output += '</a>';
             output += '</div>';

             output += '<div class="media-body">';
               output += '<h5 class="media-heading"><a href="' + pdpUrl + '" class="bootstrap-title">' + title + '</a></h5>';
               output += '<div class="bootstrap-url">' + pdpUrl + '</div>';
             output += '</div>';

             output += '<div class="media-right">';
               output += '<button type="button" class="btn btn-success" aria-label="Left Align">';
               output += '<span class="glyphicon glyphicon-shopping-cart" aria-hidden="true"></span> Add to Cart</button>';
               output += '<h5>$' + doc.price + '</h5>';
             output += '</div>';

          output += '</div>';
        output += '</div>';

        return output;
      },

      init: function () {
        $(document).on('click', 'a.more', function () {
          var $this = $(this),
              span = $this.parent().find('span');

          if (span.is(':visible')) {
            span.hide();
            $this.text('more');
          }
          else {
            span.show();
            $this.text('less');
          }

          return false;
        });
      }
    });

    Manager.addWidget(new AjaxSolr.ResultWidget({
      id: '${resultsId}',
      target: '${resultsTarget}'
    }));
</c:if>
