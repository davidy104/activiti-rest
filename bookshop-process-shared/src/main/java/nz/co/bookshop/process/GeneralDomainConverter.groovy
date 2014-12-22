package nz.co.bookshop.process

interface GeneralDomainConverter<D,T> {
	T fromDomain(D domain,Object... params)
	D toDomain(T bean,Object... params)
}
