import {Component, OnInit} from '@angular/core';
import {BoardService} from './board.service';
import {IField} from '../field/field';


@Component({
  selector: 'app-board',
  templateUrl: './board.component.html',
  styleUrls: ['./board.component.css']
})
export class BoardComponent implements OnInit {

  private HOW_MANY = 8;
  public board: IField[] = [];
  public errorMsg;

  constructor(private boardService: BoardService) {
  }

  ngOnInit() {
    this.boardService.getBoard()
      .subscribe(data => this.board = data,
        error => this.errorMsg = error);
  }

  getColor(color: string) {
    return {backgroundImage: color};
  }

  getVisibleBoard(x: number, y: number) {
    let partOfBoard: IField[] = [];
    let next = 0;
    for (let i = 0; i < this.board.length; i++) {
      let field = this.board[i];
      if (this.isBetween(field, x, y)) {
        partOfBoard[next] = field;
        next++;
      }
    }
    return this.board;
  }

  private isBetween(field, x, y) {
    return (field.cordX <= x + this.HOW_MANY && field.cordX >= x - this.HOW_MANY)
      && (field.cordY <= y + this.HOW_MANY && field.cordY >= y - this.HOW_MANY);
  }

  onSelect(field: IField) {
    window.scrollTo(field.cordX+200, field.cordY+200);
  }
}
