package com.rianrishu.fitverse.data.model.mapper

interface Mapper<Network, Domain> {

    fun toDomainModel(network: Network): Domain

    fun toDomainList(network: List<Network>): List<Domain>

    fun toNetwork(domain: Domain): Network
}
