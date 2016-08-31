package com.example

class HomeController extends ApiController {

    def index() {
        renderResponse([viewModel: []], '/index')
    }
}
