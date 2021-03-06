

	$(document).ready(function () {
		$(document).on("scroll", onScroll);
 
		$('a[href^="#"]').on('click', function (e) {
			e.preventDefault();
			$(document).off("scroll");
 
			$('a').each(function () {
				$(this).removeClass('navactive');
			})
			$(this).addClass('navactive');
 
			var target = this.hash;
			$target = $(target);
			$('html, body').stop().animate({
				'scrollTop': $target.offset().top+2
			}, 500, 'swing', function () {
				window.location.hash = target;
				$(document).on("scroll", onScroll);
			});
		});
		
		$('a[href*="#cart"]').on('click', function(e){
			if($("input[name=orderID]").val() != "" && $("input[name=orderID]").val() != null){
				$('#cart').css('display', 'block');
				window.location.hash = '#cart';
			}
			else {
				alert("Your shopping cart is empty! Add your item from our menu.");
			}
		});
		
		if (window.location.href.indexOf("#cart") > -1) {
			$('#cart').css('display', 'block');
			window.location.hash = '#cart';
		}
		
		$('.item_qty').on('change paste keyup', function(){
			var product_id = $(this).attr("data-id");
			var product_qty = $(this).val();
			$.ajax({
				  method: "GET",
				  url: "updateProductQty",
				  data: {"product_id": product_id, "qty": parseInt(product_qty, 10)}
				}).done(function() {
					location.reload();
					window.location.hash = '#cart';
				});
		})

		
	});
 
	function onScroll(event){
		var scrollPosition = $(document).scrollTop();
		$('.nav li a').each(function () {
			var currentLink = $(this);
			var refElement = $(currentLink.attr("href"));
			if (refElement.position().top <= scrollPosition && refElement.position().top + refElement.height() > scrollPosition) {
				$('ul.nav li a').removeClass("navactive");
				currentLink.addClass("navactive");
			}
			else{
				currentLink.removeClass("navactive");
			}
		});
	
       
        $(function(){
            $('#foodMenu').mixitup({
                targetSelector: '.item',
                transitionSpeed: 350
            });
        });

          $(function() {
            $( "#datepicker" ).datepicker();
        });
    
    };
    
    
//    $('.product_item').each(function(index){
//    	console.log("index: " + index);
//    	var product_id = $(this).find('input[name="product_id"]').val();
//    	var product_qty = $(this).find('#product_qty_' + product_id).val();
//    	console.log("product_id = " + product_id + ", product_qty = " + product_qty);
//    })
    
    function product_addtocart(product_id){
    	var product_qty = $('#product_qty_' + product_id).val();
    	console.log("product_id = " + product_id + ", product_qty = " + product_qty);
    	var cart_qty = $('#scqty').text();
    	if(cart_qty == ""){ cart_qty = "0"}
    	console.log("cart_qty = " +cart_qty);
    	var total_qty = parseInt(cart_qty) + parseInt(product_qty);
    	console.log("total_qty: " + total_qty);
    	$('#scqty').text(total_qty);
    }
