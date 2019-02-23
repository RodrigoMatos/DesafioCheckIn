export class MenuConfig {

    public config: any = {};

    constructor() {
        this.config = {
            items: [
                {
                    titulo: 'CheckIn',
                    url: '/checkin',
                    id: 'checkin-item'
                },
                {
                    titulo: 'Hospedes',
                    url: '/hospede',
                    id: 'hospede-item'
                },
            ]
        };
    }

}
