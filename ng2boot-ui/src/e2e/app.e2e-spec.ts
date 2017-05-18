import { Ng2bootUiPage } from './app.po';

describe('ng2boot-ui App', () => {
  let page: Ng2bootUiPage;

  beforeEach(() => {
    page = new Ng2bootUiPage();
  });

  it('should display message saying app works', () => {
    page.navigateTo();
    expect(page.getParagraphText()).toEqual('app works!');
  });
});
