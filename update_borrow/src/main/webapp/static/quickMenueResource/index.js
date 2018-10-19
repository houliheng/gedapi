var wall;
(function($) {
	
	var setting = {}, wall;
	
	var colour = [
		"#16A085",
		"#27AE60",
		"#2980B9",
		"#8E44AD",
		"#2C3E50",
		"#F39C12",
		"#D35400",
		"#C0392B",
		"#870000"
	];

	var func = {
		preload: function() {
			var images = []
			for (var i = 1 ; i < 50 ; ++i) {
				images[i] = new Image();
				images[i].src = "";
				images[i].onload = function() {
				}
			}
		},
		color: function(value) {
			$(".free-wall .brick").each(function() {
				var backgroundColor = colour[colour.length * Math.random() << 0];
				var bricks = $(this).find(".nested");
				!bricks.length && (bricks = $(this));
				bricks.css({
					backgroundColor: backgroundColor
				});
				bricks.attr("data-bgcolor", backgroundColor);
			});
		},
		layout: function() {
			var lwidth = $(window).width();
			wall = new freewall('.free-wall');
			
			wall.reset({
				selector: '> div',
				animate: true,
				cellW: 160,
				cellH: 160,
				onResize: function() {
					var cwidth = wall.container.width();
					wall.container.find('.full-width')
					.each(function(index, item){
						wall.fixSize({
							block: item,
							width: cwidth
						});
					});
					wall.fitWidth();
				},
				onComplete: function(lastItem, lastBlock, setting) {
					wall.container.find(".example-draggable").removeAttr("data-position");
				}
			});
			wall.fitWidth();
			$(window).trigger("resize");

			wall.container.find(".brick").each(function() {
				var $item = $(this);
				$item.attr({
					"data-class": $item.attr("class"),
					"data-style": $item.attr("style")
				});
			});

			// for responsive demo;
			$(".reponsive-block li>a").click(function() {
				var viewWidth = $(window).width();
				var preWidth = $(this).data("width");
				
				if (preWidth != "auto" && (preWidth - viewWidth) > 0) {
					alert('The screen\'s width not enought to test this size');
					return;
				}

				$(".reponsive-block li>a").removeClass("active");
				var preWidth = $(this).data("width");
				var margin = "10px auto";
				preWidth == "auto" && (margin = "10px");
				wall.container.css({
					margin: margin,
					width: preWidth
				});

				$(this).addClass("active");
				var cwidth = wall.container.width();
				wall.container.find('.full-width')
				.each(function(index, item){
					wall.fixSize({
						block: item,
						width: cwidth * 0.88
					});
				});
				wall.fitWidth();
			});

			if ("onhashchange" in window) {
			    window.onhashchange = function () {
			        hashChanged(window.location.hash);
			    }
			} else {
			    var storedHash = window.location.hash;
			   	setInterval(function () {
			        if (window.location.hash != storedHash) {
			            storedHash = window.location.hash;
			            hashChanged(storedHash);
			        }
			    }, 100);
			}

			function hashChanged(hash) {
				if (!hash || hash == "#") {
					wall.container.find(".brick").each(function() {
						var $item = $(this).removeAttr("style");

						$item.removeAttr("data-width");
						$item.removeAttr("data-height");
						
						$item.attr({
							"class": $item.attr("data-class")
						});

						$item.css({
							backgroundColor: $item.attr("data-bgcolor")
						});
					});
					
					//wall.unFilter();
				} else {
					$(hash).trigger("click");
				}
				$(".header")[0].scrollIntoView(true);
			}
		},
		logo: function() {},
		options: function() {},
		events: function() {},
		methods: function() {},
		share: function() {},
		finish: function() {}
	};
	
	window.app = {
		config: function(key, data) {
			setting[key] = data;
		},
		setup: function(options) {
			for (var i in options) {
				if (options.hasOwnProperty(i)) {
					func[i](options[i]);
				}
			}
			func['finish']();
		}
	};


})(window.Zepto || window.jQuery);
