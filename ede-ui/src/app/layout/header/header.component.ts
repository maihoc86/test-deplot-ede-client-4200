import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'app-header',
  templateUrl: './header.component.html',
  styleUrls: [
    './header.component.css',

		'../../../assets/css/bootstrap/css/bootstrap.css',

		'../../../assets/css/docs.css',
		'../../../assets/css/prettify.css',
		'../../../assets/css/font-awesome/css/font-awesome.min.css',

    '../../../assets/js/datetimepicker/bootstrap-datetimepicker.min.css',
    '../../../assets/js/owl-carousel/owl.carousel.css',
    '../../../assets/css/themecss/lib.css',
    '../../../assets/js/jquery-ui/jquery-ui.min.css',
    '../../../assets/js/minicolors/miniColors.css',

    '../../../assets/css/themecss/so_searchpro.css',
    '../../../assets/css/themecss/so_megamenu.css',
    '../../../assets/css/themecss/so-categories.css',
    '../../../assets/css/themecss/so-listing-tabs.css',
    '../../../assets/css/themecss/so-newletter-popup.css',

    '../../../assets/css/footer/footer1.css',
    '../../../assets/css/header/header1.css',
    '../../../assets/css/theme.css',
    '../../../assets/css/responsive.css'
  ]
})
export class HeaderComponent implements OnInit {

  constructor() { }

  ngOnInit(): void {
  }

}
