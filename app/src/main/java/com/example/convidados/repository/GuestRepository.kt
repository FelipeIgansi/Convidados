package com.example.convidados.repository

class GuestRepository  private constructor(){

    companion object{
        /*** Singleton explicação
         * É uma forma de garantir que algo seja executado APENAS uma vez
         * Nesse caso na primeira vez que exevutar esse código, será setado a instancia de GuestRepository
         * Mas se for chamado mais uma vez apenas retornará o próprio repository
         *
         *
            private lateinit var repository : GuestRepository
            fun getInstance(): GuestRepository {
                if (!::repository.isInitialized){
                    repository = GuestRepository()
                }
                return repository
            }
         *
         * */
        private lateinit var repository : GuestRepository
        fun getInstance(): GuestRepository {
            if (!Companion::repository.isInitialized){
                repository = GuestRepository()
            }
            return repository
        }
    }
}